package kr.co.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.MemberService;
import kr.co.vo.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService service;
	
	// 회원가입 뷰
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public void getRegister() throws Exception{
		logger.info("get register");
	}
	
	// 회원가입 post
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String postRegister(MemberVO vo) throws Exception{
		logger.info("post register");
		
		service.register(vo);
		
		return null;
	}
	
	// 로그인
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(MemberVO vo, HttpServletRequest req, RedirectAttributes rttr) throws Exception{
		logger.info("login");
		
		HttpSession session = req.getSession();
		MemberVO login = service.login(vo);
		
		if(login == null) {
			session.setAttribute("member", null);
			rttr.addFlashAttribute("msg", false);
		}else {
			session.setAttribute("member", login);
		}
		
		return "redirect:/";
	}
	
	// 로그아웃
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session)throws Exception{
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 회원정보 수정 뷰
	@RequestMapping(value="/memberUpdateView", method=RequestMethod.GET)
	public String memberUpdateView() throws Exception{
		logger.info("memberUpdateView");
		
		return "member/memberUpdateView";
	}
	
	// 회원정보 수정
	@RequestMapping(value="/memberUpdate", method=RequestMethod.POST)
	public String memberUpdate(MemberVO vo, HttpSession session)throws Exception{
		logger.info("mebmerUpdate");
		
		service.memberUpdate(vo); //수정 버튼 누르면, 파라미터들을 service.memberUpdate(vo)에 넣어줘서 service로 보내	
		session.invalidate(); // 세션 끊고 로그인 페이지로 리다이렉트.
		
		return "redirect:/";
	}
}
