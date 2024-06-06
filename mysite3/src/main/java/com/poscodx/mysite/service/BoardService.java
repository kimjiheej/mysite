package com.poscodx.mysite.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public void addContents(BoardVo vo) {

	}

//	public BoardVo getContents(Long no) {
//
//	}
//
//	public BoardVo getContents(Long boardNo, Long userNo) {
//
//	}

	public void updateContents(BoardVo vo) {

	}

	public void deleteContents(Long boardNo, Long userNo) {

	}

	public List<BoardVo> getContentsList(int currentPage, int boardPerPage) {
		return boardRepository.findAll(currentPage, boardPerPage);
	}

	public int getTotalBoard() {
		return boardRepository.getTotalBoard();
	}
	
	
	public BoardVo getBoard(String no) {
		return boardRepository.getBoard(no);
	}
	
	public void modifyHit(String no) {
		boardRepository.modifyHit(no);
	}
	
	public void delete(String no) {
		boardRepository.deleteBoard(no);
	}
	
}
