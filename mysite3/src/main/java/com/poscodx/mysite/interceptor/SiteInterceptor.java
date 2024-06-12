package com.poscodx.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	private SiteService  siteService;

	public SiteInterceptor(SiteService  siteService) {
		this.siteService = siteService;
	}
	
	// 쿼리 날리는 것을 줄이기 위해 ! 즉 효율성을 높이기 위해서 사용한다 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SiteVo siteVo = (SiteVo)request.getServletContext().getAttribute("sitevo");
		if(siteVo == null) {
			siteVo = siteService.getSite();
			request.getServletContext().setAttribute("sitevo", siteVo);
		}
		return true;
	}

}