package com.poscodx.mysite.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.controller.action.main.MainAction;


public class MainServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
  
	
	@Override
	public void init() throws ServletException {
		String config = getServletConfig().getInitParameter("config");
		System.out.println("MainController.init() called:" + config);
		
		super.init();
	}

	@Override
	protected Action getAction(String actionName) {
		return new MainAction();
	}
}
