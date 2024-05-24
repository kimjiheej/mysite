package com.poscodx.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.mysite.vo.GuestbookVo;



public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("a");
		

		if("insert".equals(action)) {
			
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContents(content);
			
			new GuestbookDao().insert(vo);
			response.sendRedirect(request.getContextPath() + "/guestbook");

			
		}
	
		else if("delete".equals(action)) {
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			GuestbookDao dao = new GuestbookDao();
			dao.delete(no, password);

			response.sendRedirect(request.getContextPath() + "/guestbook");
			} else if("deleteform".equals(action)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
			rd.forward(request, response);	
			
		} else {
			
	List<GuestbookVo> list = new GuestbookDao().findAll();
			
			request.setAttribute("list", list);
			request
				.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp")
				.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
/*
if("add".equals(action)) {
String name = request.getParameter("name");
String password= request.getParameter("password");
String message = request.getParameter("message");

GuestbookVo vo = new GuestbookVo();
vo.setName(name);
vo.setPassword(password);
vo.setContents(message);

new GuestbookDao().insert(vo);

response.sendRedirect("/guestbook2/gb");

} else  if("delete".equals(action)) {

String no = request.getParameter("no");
String password = request.getParameter("password");
GuestbookDao dao = new GuestbookDao();
dao.delete(no, password);

response.sendRedirect(request.getContextPath() + "/gb");
} else if("deleteform".equals(action)) {
RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
rd.forward(request, response);	
}
else {
List<GuestbookVo> list = new GuestbookDao().findAll();

request.setAttribute("list", list);

RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
rd.forward(request,response);
}
}
*/