package kr.co.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.BoardService;
import kr.co.vo.BoardVO;
import kr.co.vo.PageMaker;
import kr.co.vo.SearchCriteria;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardService service;
		
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
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		
		model.addAttribute("pageMaker", pageMaker); // 게시판 하단의 페이징 관련, 이전페이지, 페이지 링크, 다음 페이지
		
		return "board/list";
	}
	
	// 게시판 조회
	@RequestMapping(value="/board/readView", method=RequestMethod.GET)
	public String read(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("read");
		
		model.addAttribute("read", service.read(boardVO.getBno())); //bno값들은 BoardVO에 들어있기 때문에 서비스 실행시 그 번호를 넣어서 read라는 이름으로 값을 저장.
		model.addAttribute("scri",scri); //검색 결과 유지하기 위해 scri 담기
		
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
	@RequestMapping(value="/board/update", method=RequestMethod.POST)
	public String update(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr)throws Exception{
		logger.info("update");
		
		service.update(boardVO);
		
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());

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
	
}