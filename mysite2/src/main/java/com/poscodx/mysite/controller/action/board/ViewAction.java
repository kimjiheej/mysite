package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String no = request.getParameter("no");
		String currentPage = request.getParameter("curPage");
		BoardVo board = new BoardDao().getBoard(no);
		new BoardDao().modifyHit(no);
		request.setAttribute("board", board);
		request.setAttribute("currentPage", currentPage);
		request.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(request, response);
		
	}

}
