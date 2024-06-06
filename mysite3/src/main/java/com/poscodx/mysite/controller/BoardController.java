package com.poscodx.mysite.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    /*
     	String no = request.getParameter("no");
		String currentPage = request.getParameter("curPage");
		BoardVo board = new BoardDao().getBoard(no);
		new BoardDao().modifyHit(no);
		request.setAttribute("board", board);
		request.setAttribute("currentPage", currentPage);
		request.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(request, response);
	}
     */
	
	@RequestMapping(value ="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, 
            @RequestParam(value = "curPage", required = false) String current, 
            Model model) {
			
		String currentPage = current;
		BoardVo board = boardService.getBoard(String.valueOf(no));
		boardService.modifyHit(String.valueOf(no));
		  model.addAttribute("board", board);
	        model.addAttribute("currentPage", currentPage);
	        return "board/view";
	}

	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no) {
		
		boardService.delete(String.valueOf(no));

		return "redirect:/board";
	}
	
	  @RequestMapping(value="/writeform", method=RequestMethod.GET)
	    public String writeForm(HttpSession session, Model model) {
	        UserVo authUser = (UserVo) session.getAttribute("authUser");
	        return "board/write"; // 작성 폼 페이지로 포워드
	    }
	
	   @RequestMapping(value="/write", method=RequestMethod.POST)
	  public String write(HttpSession session, 
	                      @RequestParam("title") String title, 
	                      @RequestParam(value = "no", required = false) Long num,
	                      @RequestParam("contents") String contents, 
	                      Model model) {
	      UserVo authUser = (UserVo) session.getAttribute("authUser");
	      if(authUser == null) {
	          return "redirect:/board"; // Redirect to board page if user is not authenticated
	      } else {
	          model.addAttribute("authUser", authUser);
	      }

	      Date currentDate = new Date();
	      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	      String reg_Date = formatter.format(currentDate);

	      BoardVo vo = new BoardVo();
	      vo.setTitle(title);
	      vo.setContents(contents);
	      vo.setReg_date(reg_Date);
	      vo.setUser_no(authUser.getNo());

	      if (num == null) {
	          vo.setG_no(boardService.getNextNumber());
	          vo.setDepth(0);
	          vo.setO_no(1);
	      } else {
	          String no = String.valueOf(num); // Convert num to String
	          BoardVo originVo = boardService.getBoard(no);
	          vo.setReg_date(reg_Date);
	          vo.setG_no(originVo.getG_no());
	          vo.setO_no(originVo.getO_no() + 1);
	          vo.setDepth(originVo.getDepth() + 1);
	          boardService.Update(originVo.getG_no(), originVo.getO_no() + 1);
	      }

	      boardService.insert(vo); // Assuming this method saves the board entry
	      return "redirect:/board"; // Redirect to board page after creating or updating board entry
	  }
	   
	   @RequestMapping(value="/modifyform", method=RequestMethod.GET)
	   public String modifyForm(@RequestParam("no") String no, HttpSession session, Model model) {
	       UserVo authUser = (UserVo) session.getAttribute("authUser");
	       if (authUser == null) {
	           return "redirect:/user/login";
	       }
	       BoardVo list = boardService.getBoard(no);
	       model.addAttribute("list", list);
	       return "board/modify";
	   }
	   
	  
	   @RequestMapping(value="/modify", method=RequestMethod.POST)
	   public String modify(@RequestParam("no") Long no, @RequestParam("title") String title, @RequestParam("contents") String contents ) {
		   boardService.modifyBoard(String.valueOf(no), title,contents);
		    return "redirect:/board/view/" + no;
	   }
}