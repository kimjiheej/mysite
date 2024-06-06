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
   
	public void insert(BoardVo vo) {
		sqlSession.insert("board.insertBoard", vo);
	}
	
	public int getNextNum() {
	    return sqlSession.selectOne("board.getNextNumber");
	}
	
	public void modifyBoard(String no, String title, String contents) {
		sqlSession.update("board.modifyBoard", Map.of("no", no, "title", title, "contents", contents));
	}
	
	
	public void updateBoard(int g_no, int o_no) {
	       sqlSession.update("board.updateBoard",Map.of("g_no",g_no,"o_no",o_no));
		
	}
	
}