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

        int showingPages = 5;  // 한 번에 보여줄 페이지 번호 개수
        int boardPerPage = 7;  // 한 페이지에 표시할 게시글 개수

        BoardDao boardDao = new BoardDao();
        List<BoardVo> lists = boardDao.findPage(current, boardPerPage);

        int totalItems = boardDao.getTotalBoard();
        int totalPages = (int) Math.ceil((double) totalItems / boardPerPage);

        int startPage = ((current - 1) / showingPages) * showingPages + 1;
        int endPage = Math.min(startPage + showingPages - 1, totalPages);

        boolean[] emptyPages = new boolean[totalPages + 1];  // 전체 페이지 개수에 맞게 배열 크기 설정
        for (int i = 1; i <= totalPages; i++) {
            emptyPages[i] = boardDao.findPage(i, boardPerPage).isEmpty();
        }

        request.setAttribute("start", startPage);
        request.setAttribute("end", endPage);
        request.setAttribute("totalPage", totalPages);
        request.setAttribute("list", lists);
        request.setAttribute("current", current);
        request.setAttribute("totalItem", totalItems);
        request.setAttribute("onePagesItem", boardPerPage);
        request.setAttribute("emptyPages", emptyPages);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
        rd.forward(request, response);
    }
}