package kr.co.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.dao.BoardDAO;
import kr.co.util.FileUtils;
import kr.co.vo.BoardVO;
import kr.co.vo.SearchCriteria;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Inject
	private BoardDAO dao;
	
	// 게시글 작성
	@Override
	public void write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception {
		dao.write(boardVO, mpRequest);
		
		List<Map<String,Object>>list = fileUtils.parseInsertFileInfo(boardVO, mpRequest);
		int size = list.size();
		for(int i=0; i<size; i++) { //첨부파일 여러개 등록
			dao.insertFile(list.get(i));
		}
	}

	// 게시물 목록 조회
	@Override
	public List<BoardVO> list(SearchCriteria scri) throws Exception {
		return dao.list(scri);
	}

	// 게시물 총 갯수
	@Override
	public int listCount(SearchCriteria scri) throws Exception {
		return dao.listCount(scri);
	}
	
	// 게시물 조회
	@Override
	public BoardVO read(int bno) throws Exception {
		return dao.read(bno);
	}

	// 게시물 수정
	@Override
	public void update(BoardVO boardVO) throws Exception {
		dao.update(boardVO);
	}

	// 게시물 삭제
	@Override
	public void delete(int bno) throws Exception {
		dao.delete(bno);
	}

	// 첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int bno) throws Exception {
		return dao.selectFileList(bno);
	}

	// 첨부파일 수정
	@Override
	public void update(BoardVO boardVO, String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest) throws Exception{
		dao.update(boardVO);
		
		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(boardVO, files, fileNames, mpRequest); //파일 업데이트할 값들을 list에 담는다.
		Map<String, Object> tempMap = null;
		int size = list.size();
		for(int i=0; i<size; i++) { //fileUtils.parseUpdateFileInfo()결과의 size만큼 for문 돌려
			tempMap = list.get(i); //tempMap에 list.get(i)를 담고 
			if(tempMap.get("IS_NEW").equals("Y")) { //if문을 이용하여 tempMap에서 IS_NEW를 꺼내와서 값이Y와 같으면
				dao.insertFile(tempMap); //dao.insertFile(tempMap)를 실행
			}else {
				dao.updateFile(tempMap); //같지 않으면 dao.updateFile(tempMap)실행
			}
		}
	}
}
