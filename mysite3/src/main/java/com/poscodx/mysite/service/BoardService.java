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
	
	public void insert(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
	public int getNextNumber() {
	   return boardRepository.getNextNum();
	}
	
	public void Update(int g_no, int o_no) {
		boardRepository.updateBoard(g_no, o_no);
	}
	


	public void modifyBoard(String no, String title, String contents) {
		// TODO Auto-generated method stub
		boardRepository.modifyBoard(no,title,contents);
	}
	
	/*
	 public void modifyBoard(String no, String title, String contents) {
		    try (
		        Connection conn = getConnection();
		        PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET title=?, contents=?, reg_date=current_time() WHERE no=?");
		    ) {
		        pstmt.setString(1, title);
		        pstmt.setString(2, contents);
		        pstmt.setInt(3, Integer.parseInt(no));
		        pstmt.executeUpdate();
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }
		}
	 */
	/*
	 	// 게시글 그룹. order 업데이트 해주기 
	public void Update(int g_no, int o_no) {
	    try (
	        Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("UPDATE board SET o_no = o_no + 1 WHERE g_no = ? AND o_no >= ?");
	    ) {
	        pstmt.setInt(1, g_no);
	        pstmt.setInt(2, o_no);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	}
	 */
}
