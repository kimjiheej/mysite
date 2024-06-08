package com.poscodx.mysite.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
@Transactional
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public void updateContents(BoardVo vo) {

	}

	public List<BoardVo> getContentsList(int currentPage, int boardPerPage) {
		return boardRepository.findAll(currentPage, boardPerPage);
	}

	public int getTotalBoard() {
		return boardRepository.getTotalBoard();
	}
	
	public BoardVo getContents(Long no) {
		return boardRepository.getBoard(no);
	}
	
	public void modifyHit(Long no) {
		boardRepository.modifyHit(no);
	}
	
	public void deleteContents(Long no, Long userNo) {
		boardRepository.deleteBoard(no, userNo);
	}
	
	public void addContents(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
	public int getNextNumber() {
	   return boardRepository.getNextNum();
	}
	
	public void Update(int g_no, int o_no) {
		boardRepository.updateBoard(g_no, o_no);
	}

	public void modifyContents(BoardVo boardVo) {
		boardRepository.modifyBoard(boardVo);
	}
	
}
