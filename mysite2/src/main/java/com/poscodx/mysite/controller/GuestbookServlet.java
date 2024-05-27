package com.poscodx.mysite.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.controller.action.guestbook.DeleteFormGuestBook;
import com.poscodx.mysite.controller.action.guestbook.DeleteGuestBook;
import com.poscodx.mysite.controller.action.guestbook.GuestBookListAction;
import com.poscodx.mysite.controller.action.guestbook.InsertGuestBook;
import com.poscodx.mysite.controller.action.main.MainAction;
import com.poscodx.mysite.controller.action.user.JoinAction;
import com.poscodx.mysite.controller.action.user.JoinFormAction;
import com.poscodx.mysite.controller.action.user.JoinSuccess;
import com.poscodx.mysite.controller.action.user.LoginAction;
import com.poscodx.mysite.controller.action.user.LoginFormAction;
import com.poscodx.mysite.controller.action.user.LogoutAction;
import com.poscodx.mysite.controller.action.user.UpdateFormAction;
import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.mysite.vo.GuestbookVo;


public class GuestbookServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;


		
		private Map<String, Action> mapAction = Map.of(
				"insert", new InsertGuestBook(),
				"delete", new DeleteGuestBook(), 
				"deleteform", new DeleteFormGuestBook()
	);
				
		@Override
		protected Action getAction(String actionName) {
			return mapAction.getOrDefault(actionName, new GuestBookListAction());
		}
}
