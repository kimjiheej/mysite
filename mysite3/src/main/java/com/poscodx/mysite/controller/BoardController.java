package com.poscodx.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	
	@Autowired
	private BoardService boardService;
	
	// writeform 으로 전환되는 메서드이다. 
	@RequestMapping(value="/writeform", method=RequestMethod.GET)
    public String write () {
		return "board/write";
	}
	
	// 처음에 board 로 들어왔을 때에 이렇게 해주기 
	  @RequestMapping(value="", method=RequestMethod.GET)
	    public String main(@RequestParam(value="page", defaultValue="1") int current, Model model) {
	        int showingPages = 5;  // 한 번에 보여줄 페이지 번호 개수
	        int boardPerPage = 7;  // 한 페이지에 표시할 게시글 개수

	        // BoardService를 통해 필요한 데이터 가져오기
	        List<BoardVo> lists = boardService.findPage(current, boardPerPage);
	        int totalItems = boardService.getTotalBoard();
	        int totalPages = (int) Math.ceil((double) totalItems / boardPerPage);

	        int startPage = ((current - 1) / showingPages) * showingPages + 1;
	        int endPage = Math.min(startPage + showingPages - 1, totalPages);

	        boolean[] emptyPages = new boolean[totalPages + 1];  // 전체 페이지 개수에 맞게 배열 크기 설정
	        for (int i = 1; i <= totalPages; i++) {
	            emptyPages[i] = boardService.findPage(i, boardPerPage).isEmpty();
	        }

	        model.addAttribute("start", startPage);
	        model.addAttribute("end", endPage);
	        model.addAttribute("totalPage", totalPages);
	        model.addAttribute("list", lists);
	        model.addAttribute("current", current);
	        model.addAttribute("totalItem", totalItems);
	        model.addAttribute("onePagesItem", boardPerPage);
	        model.addAttribute("emptyPages", emptyPages);

	        // 정보들을 저장해서 해당 페이지로 넘겨준다 
	        return "board/list";  // JSP 페이지로 이동하기 
	  }	 
	  
	  // view 
	  
	  @RequestMapping(value="/view", method=RequestMethod.GET)
	  public String view(@RequestParam("no") int no, @RequestParam("curPage") int page, Model model) {
		  
		  BoardVo board = boardService.getBoard(String.valueOf(no));
		  model.addAttribute("board",board);
		  model.addAttribute("currentPage", page);
		  return "/board/view";
	  }
	  
	  // 게시판 삭제하기 
	  
	  @RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	  public String deleteBoard(@PathVariable("no") int no, Model model) {
		  boardService.deleteBoard(String.valueOf(no));
		  return "redirect:/board";
	  }
	  
	  
	 
}
