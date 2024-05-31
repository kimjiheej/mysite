package com.poscodx.mysite.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.action.main.MainAction;
import com.poscodx.mysite.controller.action.user.JoinAction;
import com.poscodx.mysite.controller.action.user.JoinFormAction;
import com.poscodx.mysite.controller.action.user.JoinSuccess;
import com.poscodx.mysite.controller.action.user.LoginAction;
import com.poscodx.mysite.controller.action.user.LoginFormAction;
import com.poscodx.mysite.controller.action.user.LogoutAction;
import com.poscodx.mysite.controller.action.user.UpdateForm;
import com.poscodx.mysite.controller.action.user.UpdateFormAction;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;


public class UserServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
		"joinform", new JoinFormAction(),
		"join", new JoinAction(),
		"joinsuccess", new JoinSuccess(),
		"loginform", new LoginFormAction(),
		"login" , new LoginAction(),
		"logout", new LogoutAction(), 
		"updateform", new UpdateFormAction(),
		"update", new UpdateForm()
	);
	
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new MainAction());
	}
}