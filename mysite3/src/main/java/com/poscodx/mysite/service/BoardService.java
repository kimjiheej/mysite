package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	
    @Autowired
    private BoardRepository boardRepository;
    
    public List<BoardVo> findPage(int current, int boardPerPage) {
        return boardRepository.findPage(current, boardPerPage);
    }
    
    public int getTotalBoard() {
        return boardRepository.getTotalBoard();
    }
    
    public BoardVo getBoard(String no) {
    	boardRepository.modifyHit(no);
    	return boardRepository.getBoard(no);
    }
    
    public void deleteBoard(String no) {
    	boardRepository.deleteBoard(no);
    	
    }
}