package com.poscodx.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestbookLogRepository {
	private SqlSession sqlSession;
	
	public GuestbookLogRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insert() {
		return sqlSession.update("guestbooklog.insert");
	}
	
	public int update() {
		return sqlSession.update("guestbooklog.update-increase");
	}
	
	public int update(Long no) {
		return sqlSession.update("guestbooklog.update-decrease", no);
	}
}