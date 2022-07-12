package kr.co.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.MemberService;
import kr.co.vo.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService service;
	
	@Inject
	BCryptPasswordEncoder pwdEncoder;
	
	// 회원가입 뷰
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public void getRegister() throws Exception{
		logger.info("get register");
	}
	
	// 회원가입 post
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String postRegister(MemberVO vo) throws Exception{
		logger.info("post register");
		
		// 아이디 중복 확인
		int result = service.idChk(vo);
		try{
			if(result==1) {               //입력 된 아이디가 존재하면
				return "/member/register"; //회원가입 페이지로 돌아가
			}else if(result==0) {		  // 존재하지 않으면 service.register로
				String inputPass = vo.getUserPass();
				String pwd = pwdEncoder.encode(inputPass);
				vo.setUserPass(pwd); // 비번 암호화 해서 vo에 다시 넣기.
				
				service.register(vo);   
			}
		}catch(Exception e){
			throw new RuntimeException();
		}
		
		return "redirect:/";
	}
	
	// 로그인
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(MemberVO vo, HttpServletRequest session, RedirectAttributes rttr) throws Exception{
		logger.info("login");
		
		session.getAttribute("member");
		MemberVO login = service.login(vo);
		
		// 이거 안쓰면 db에 없는 아이디를 입력하고 로그인하면 에러코드 발생
			if(login == null ) { 
				rttr.addFlashAttribute("msg", false);
				return "redirect:/";
			}
				
		boolean pwdMatch = pwdEncoder.matches(vo.getUserPass(), login.getUserPass());
											// 입력된 비밀번호     ,  암호화된 비밀번호  (인코딩 되지 않은 pw, 인코딩 된 pw 비교!)
		
		if(login != null && pwdMatch == true) {
			session.setAttribute("member", login);
		}else {
			session.setAttribute("member", null);
			rttr.addFlashAttribute("msg", false);	
			
			/* return "redirect:/"; */
		}	
		//System.out.println("입력된 비번:"+vo.getUserPass());
		//System.out.println("암호화된 비번:"+login.getUserPass());
		System.out.println("login:"+login);
		System.out.println("pwdMatch:"+pwdMatch);
		System.out.println("login:"+vo);
		return "redirect:/board/main";
	}
	
	// 로그아웃
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) throws Exception{
		
		session.invalidate();
		
		return "redirect:/navlogin/login";
	}
	
	// 회원정보 수정 뷰
	@RequestMapping(value="/memberUpdateView", method=RequestMethod.GET)
	public String memberUpdateView() throws Exception{
		logger.info("memberUpdateView");
		
		return "member/memberUpdateView";
	}
	
	// 회원정보 수정
	@RequestMapping(value="/memberUpdate", method=RequestMethod.POST)
	public String memberUpdate(MemberVO vo, HttpSession session) throws Exception{
		logger.info("mebmerUpdate");
		
		service.memberUpdate(vo); //수정 버튼 누르면, 파라미터들을 service.memberUpdate(vo)에 넣어줘서 service로 보내	
		session.invalidate(); // 세션 끊고 로그인 페이지로 리다이렉트.
		
		return "redirect:/";
	}
	
	// 회원탈퇴 뷰
	@RequestMapping(value="/memberDeleteView", method=RequestMethod.GET)
	public String memberDeleteView() throws Exception{
		logger.info("memberDeleteView");
		
		return "member/memberDeleteView";
	}
	
	// 회원탈퇴 
	@RequestMapping(value="/memberDelete", method=RequestMethod.POST)
	public String memberDelete(MemberVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
		logger.info("memberDelete");
		
		// ajax가 해줘!
	/*	// 세션에 있는 member를 가져와 member변수에 넣어줌.
		MemberVO member = (MemberVO) session.getAttribute("member");
		// 세션에 있는 비밀번호
		String sessionPass = member.getUserPass();
		// vo로 들어오는 비밀번호
		String voPass = vo.getUserPass();
		System.out.println("member:"+member);
		if(!(sessionPass.equals(voPass))) { // 세션 pass와 vo의 pass 비교
			rttr.addFlashAttribute("msg", false); // 다르면 메시지 뜸
			
			return "redirect:/member/memberDeleteView";
		}   */ 
		service.memberDelete(vo);
		session.invalidate();
		return "redirect:/";
	}
	
	// 패스워드 체크
	@ResponseBody //controller에서 jsp로 json 전달하기 위해 사용
	@RequestMapping(value="/passChk", method=RequestMethod.POST)
	public int passChk(MemberVO vo) throws Exception{
		
		int result = service.passChk(vo);
		return result;
	}
	
	// 아이디 중복 체크
	@ResponseBody
	@RequestMapping(value="idChk", method=RequestMethod.POST)
	public int idChk(MemberVO vo) throws Exception{
		
		int result = service.idChk(vo);
		return result;
	}
	
	
}