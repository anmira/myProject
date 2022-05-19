package kr.co.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.BoardService;
import kr.co.service.ReplyService;
import kr.co.vo.BoardVO;
import kr.co.vo.PageMaker;
import kr.co.vo.ReplyVO;
import kr.co.vo.SearchCriteria;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardService service;
	
	@Inject
	ReplyService replyService;
		
	// 메인 진입 전 화면
	/*
	 * @RequestMapping(value="/board/main", method=RequestMethod.GET) public String
	 * main() throws Exception{ logger.info("main"); return "board/main"; }
	 */
	
	// 메인화면
	@RequestMapping(value="/board/mainPage", method=RequestMethod.GET)
	public String mainPage() throws Exception{
		logger.info("mainPage");
		String springVersion = org.springframework.core.SpringVersion.getVersion();
		System.out.println("스프링 프레임워크버전:"+springVersion);
		return "board/mainPage";
		}
	
	// 게시판 글 작성 화면
	@RequestMapping(value="/board/writeView", method=RequestMethod.GET)
	public void writeView() throws Exception{
		logger.info("writeView");
	}
	
	// 게시판 글 작성
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String write(BoardVO boardvo) throws Exception{
		logger.info("write");				
		
		service.write(boardvo);
	
		return "redirect:/";
	}
	
	// 게시판 목록 조회
	@RequestMapping(value="/board/list", method=RequestMethod.GET)
	public String list(Model model, @ModelAttribute("scri") SearchCriteria scri) throws Exception{ //db->dao->service 순으로 가져온 데이터들을 jsp에 뿌려주기.
		logger.info("list");
		
		model.addAttribute("list", service.list(scri)); // service.list()에 담긴 데이터를 "list"라고 담기, 게시판의 글 리스트.
		System.out.println("list:"+model);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		
		model.addAttribute("pageMaker", pageMaker); // 게시판 하단의 페이징 관련, 이전페이지, 페이지 링크, 다음 페이지
		
		return "board/list";
	}
	
	// 게시판 조회
	@RequestMapping(value="/board/readView", method=RequestMethod.GET)
	public String read(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("readView");
		
		model.addAttribute("read", service.read(boardVO.getBno())); //bno값들은 BoardVO에 들어있기 때문에 서비스 실행시 그 번호를 넣어서 read라는 이름으로 값을 저장.
		model.addAttribute("scri",scri); //검색 결과 유지하기 위해 scri 담기
		
		// 댓글 불러오기
		List<ReplyVO>replyList = replyService.readReply(boardVO.getBno());
		model.addAttribute("replyList", replyList);
		
		System.out.println("readView:"+model);
		return "board/readView";
	}
	
	// 게시판 수정뷰
	@RequestMapping(value="/board/updateView", method=RequestMethod.GET)
	public String updateView(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("updateView");
		
		model.addAttribute("update", service.read(boardVO.getBno()));
		model.addAttribute("scri",scri); //검색 결과 유지하기 위해 scri 담기
	
		return "board/updateView";
	}
	
	// 게시판 수정
	@RequestMapping(value="/board/update", method=RequestMethod.POST)	// 리다이렉트(페이지 이동) 직전 값을 저장한뒤 리다이렉트 된곳에 값을 넘겨주는 역할
	public String update(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr)throws Exception{
		logger.info("update");
		
		service.update(boardVO);
		
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		System.out.println("update:"+scri);
		return "redirect:/board/list";
	}
	
	// 게시판 삭제
	@RequestMapping(value="/board/delete", method=RequestMethod.POST)
	public String delete(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("delete");
		
		service.delete(boardVO.getBno());
		
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	// 댓글 작성
	@RequestMapping(value="/replyWrite", method=RequestMethod.POST)
	public String replyWrite(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("replyWrite");               
		
		replyService.writeReply(vo);
		
		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		// SearchCriteria의 값으르 넣어서 댓글을 저장 한 뒤 원래 페이지로 redirect 이동.
		
		return "redirect:/board/readView";
	}
	
	// 댓글 수정 뷰
	@RequestMapping(value="/replyUpdateView", method=RequestMethod.GET)
	public String replyUpdateView(ReplyVO vo, SearchCriteria scri, Model model) throws Exception{
		logger.info("replyUpdateView");
		
		model.addAttribute("replyUpdate", replyService.selectReply(vo.getRno()));
		model.addAttribute("scri", scri);
		
		return "board/replyUpdateView";
	}
	
	// 댓글 수정
	@RequestMapping(value="/replyUpdate", method=RequestMethod.POST)
	public String replyUpdate(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("replyUpdate");
		
		replyService.updateReply(vo);
		
		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/readView";
	}
	
	// 댓글 삭제 뷰
	@RequestMapping(value="/replyDeleteView", method=RequestMethod.GET)
	public String replyDeleteView(ReplyVO vo, SearchCriteria scri, Model model) throws Exception{
		logger.info("replyDeleteView");
			
		model.addAttribute("replyDelete", replyService.selectReply(vo.getRno()));
		model.addAttribute("scri", scri);
			
		return "board/replyDeleteView";
		}
	
	// 댓글 삭제
	@RequestMapping(value="/replyDelete", method=RequestMethod.POST)
	public String replyDelete(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info("replyDelete");
		
		replyService.deleteReply(vo);
		
		rttr.addAttribute("bno", vo.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/readView";
	}
	
	
	
	
}