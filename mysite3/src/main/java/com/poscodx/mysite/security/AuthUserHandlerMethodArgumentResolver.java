package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.poscodx.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	// handler 가 작동할 때마다 동작하게 된다 
	// 매번 실행이 되는 것이다 
	// AuthUser 와 관련된 것을 가로채서 일을 해주는 것이다. 
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
	   AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
	   
	   // @AuthUser 가 안 붙어 있다면, 
	   
	   if(authUser == null) {
		   return false;
	   }
	
	   // AuthUser 가 null 이 아니다 
	   
	  
	   // 파라미터 타입이 USerVo 가 아니면, 
	 if (!parameter.getParameterType().equals(UserVo.class)) {
		 return false;
	 }
	 
	 
	 return true;
	 
	   
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		// 
		// 해당되는 view 를 찾아주게 된다 
		
		// support 해주는 파라미터가 아니라면 resolving 을 할수가 없는 것이다 
		if(!supportsParameter(parameter)) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		
		return session.getAttribute("authUser");
		
	}

}