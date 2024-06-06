package com.poscodx.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

    private SqlSession sqlSession;

    public BoardRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<BoardVo> findAll(int currentPage, int boardPerPage) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", (currentPage - 1) * boardPerPage);
        params.put("limit", boardPerPage);
        return sqlSession.selectList("board.findPage", params);
    }
    
    public int getTotalBoard() {
        return sqlSession.selectOne("board.getTotalBoard");
    }
    
    public BoardVo getBoard(String no) {
        return sqlSession.selectOne("board.getBoard", no);
    }
    
   
    public void modifyHit(String no) {
    	sqlSession.update("board.modifyHit", no);
    }
    
	public void deleteBoard(String no) {
		sqlSession.delete("board.deleteBoard", no);
	}
   
}