package com.poscodx.mysite.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
    private BoardService boardService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") String page) {

        int current = 1;
        if (!page.equals("")) {
            current = Integer.parseInt(page);
        }

        int showingPages = 5;  // Number of pages to show in pagination
        int boardPerPage = 7;  // Number of items per page

        List<BoardVo> lists = boardService.getContentsList(current, boardPerPage);
        int totalItems = boardService.getTotalBoard();
        int totalPages = (int) Math.ceil((double) totalItems / boardPerPage);

        int startPage = ((current - 1) / showingPages) * showingPages + 1;
        int endPage = Math.min(startPage + showingPages - 1, totalPages);

        boolean[] emptyPages = new boolean[totalPages + 1];
        for (int i = 1; i <= totalPages; i++) {
            emptyPages[i] = boardService.getContentsList(i, boardPerPage).isEmpty();
        }

        model.addAttribute("start", startPage);
        model.addAttribute("end", endPage);
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("list", lists);
        model.addAttribute("current", current);
        model.addAttribute("totalItem", totalItems);
        model.addAttribute("onePagesItem", boardPerPage);
        model.addAttribute("emptyPages", emptyPages);

        return "board/list";
    }
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, @RequestParam("curPage") String current, Model model) {
			
		String currentPage = current;
		BoardVo board = boardService.getBoard(String.valueOf(no));
		boardService.modifyHit(String.valueOf(no));
		  model.addAttribute("board", board);
	        model.addAttribute("currentPage", currentPage);
	        return "board/view";
	}

	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		
		boardService.delete(String.valueOf(no));

		return "redirect:/board";
	}
	
	
	
	
}