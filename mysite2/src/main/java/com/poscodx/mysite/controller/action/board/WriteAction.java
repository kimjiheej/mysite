package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user?a=login");
			return;
		} else {
			request.setAttribute("authUser", authUser);
		}
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String reg_Date = formatter.format(currentDate);
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setReg_date(reg_Date);

		Long user_no = authUser.getNo();
		vo.setUser_no(user_no);

		String no = request.getParameter("no");
		if (no == null || "".equals(no)) {
			vo.setG_no(new BoardDao().getNextNumber());
			vo.setDepth(0); // 처음 글을 쓸 때 depth를 0으로 설정
			vo.setO_no(1);
		} else {
			BoardVo originVo = new BoardDao().getBoard(no);
			vo.setReg_date(reg_Date);
			vo.setG_no(originVo.getG_no());
			vo.setO_no(originVo.getO_no() + 1);
			vo.setDepth(originVo.getDepth() + 1);
			new BoardDao().Update(originVo.getG_no(), originVo.getO_no() + 1);
		}
		new BoardDao().insert(vo);
		response.sendRedirect(request.getContextPath() + "/board");
	}
}