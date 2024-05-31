package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

public class MainAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	
    	
    	
    	
    	
    	
    	int current = 1;
        if (request.getParameter("page") != null) {
            current = Integer.parseInt(request.getParameter("page"));
        }

        // 보여줄 총 페이지 수 
        int showingPages = 5;
        // 한 페이지의 개시물의 수 
        int boardPerPage = 7; 
        
        
        // 구글링... 
        BoardDao boardDao = new BoardDao();
        List<BoardVo> lists = boardDao.findPage(current, boardPerPage);
        
        // 전체 페이지 가져오기 
        int totalItems = boardDao.getTotalBoard(); 
        int totalPages = (int) Math.ceil((double) totalItems / boardPerPage);
        int startPage = ((current - 1) / showingPages) * showingPages + 1;
        int endPage = startPage + showingPages - 1;
        if (endPage > totalPages) {
            endPage = totalPages;
        }
        
        request.setAttribute("start", startPage);
        request.setAttribute("end", endPage);
        request.setAttribute("totalPage", totalPages);
        request.setAttribute("list", lists);
        
        
        request.setAttribute("current", current);
        request.setAttribute("totalItem", totalItems); // 전체 게시물 수를 설정
        request.setAttribute("onePagesItem", boardPerPage); // 페이지당 항목 수를 설정
        
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
        rd.forward(request, response);
       
    }
}