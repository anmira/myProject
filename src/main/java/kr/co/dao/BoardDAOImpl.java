package kr.co.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.vo.BoardVO;
import kr.co.vo.SearchCriteria;

@Service
public class BoardDAOImpl implements BoardDAO{

	@Inject
	SqlSession sqlSession;
	
	// 게시글 작성
	@Override
	public void write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception {
		sqlSession.insert("boardMapper.insert", boardVO);
		
	}

	// 게시물 목록 조회
	@Override
	public List<BoardVO> list(SearchCriteria scri) throws Exception {
		return sqlSession.selectList("boardMapper.listPage", scri);
	
	}
	
	// 게시물 총 갯수
	@Override
	public int listCount(SearchCriteria srci) throws Exception {
		return sqlSession.selectOne("boardMapper.listCount", srci);
	
	}
	
	// 게시물 조회
	@Override
	public BoardVO read(int bno) throws Exception {
		return sqlSession.selectOne("boardMapper.read", bno);
	}

	// 게시물 수정
	@Override
	public void update(BoardVO boardVO) throws Exception {
		sqlSession.update("boardMapper.update", boardVO);
	}

	// 게시물 삭제
	@Override
	public void delete(int bno) throws Exception {
		sqlSession.delete("boardMapper.delete", bno);
	}

	// 첨부파일 업로드
	@Override
	public void insertFile(Map<String, Object> map) throws Exception {
		sqlSession.insert("boardMapper.insertFile", map);
	}
	
	// 첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int bno) throws Exception{
		return sqlSession.selectList("boardMapper.selectFileList", bno);
	}

	// 첨부파일 수정
	@Override
	public void updateFile(Map<String, Object> map) throws Exception{
		sqlSession.update("boardMapper.updateFile", map);
	}
}
