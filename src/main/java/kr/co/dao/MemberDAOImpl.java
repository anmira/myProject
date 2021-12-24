package kr.co.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{

	@Inject SqlSession sql;
	
	// 회원가입
	@Override
	public void register(MemberVO vo) throws Exception {
		sql.insert("memberMapper.register", vo);
		
	}

	// 로그인
	@Override
	public MemberVO login(MemberVO vo) throws Exception {
		return sql.selectOne("memberMapper.login", vo);
	}

	// 회원정보 수정
	@Override
	public void memberUpdate(MemberVO vo) throws Exception {
		sql.update("memberMapper.memberUpdate", vo);
	}
	
	// 회원탈퇴
	@Override
	public void memberDelete(MemberVO vo) throws Exception {
		sql.delete("memberMapper.memberDelete", vo);
		
	}

	// 패스워드 체크
	@Override
	public int passChk(MemberVO vo) throws Exception {
		int result = sql.selectOne("memberMapper.passChk", vo); //쿼리에서 조회한 값과 파라미터를 보내주는 값이 숫자니까 타입은 int형으로 선언.
		return result;											//MemberService에서 전달받은 파라미터를 memberMapper.xml에 보내줌. 
	}

	// 아이디 체크
	@Override
	public int idChk(MemberVO vo) throws Exception {
		int result = sql.selectOne("memberMapper.idChk", vo);
		return result;
	}

}
