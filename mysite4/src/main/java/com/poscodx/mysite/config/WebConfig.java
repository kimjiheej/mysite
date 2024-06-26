package com.poscodx.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscodx.mysite.config.web.FileUploadConfig;
import com.poscodx.mysite.config.web.LocaleConfig;
import com.poscodx.mysite.config.web.MvcConfig;
import com.poscodx.mysite.config.web.SecurityConfig;
import com.poscodx.mysite.event.ApplicationContextEventListener;
import com.poscodx.mysite.interceptor.SiteInterceptor;

@Configuration
@EnableAspectJAutoProxy
@Import({MvcConfig.class, LocaleConfig.class, SecurityConfig.class, FileUploadConfig.class})
@ComponentScan({"com.poscodx.mysite.controller", "com.poscodx.mysite.exception"})
public class WebConfig implements WebMvcConfigurer {
	
	// 이 클래스는 web.xml 을 대신하는 클래스이다. 
	// Site Interceptor
	
	// Bean : 스프링 컨텍스트에 빈을 정의한다. 
	// siteInterceptor : SiteInterceptor 를 빈으로 등록한다 
	@Bean
	public HandlerInterceptor siteInterceptor() {
		return new SiteInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(siteInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/assets/**");
	}
	
	// addInterceptor : siteInterceptor 를 모든 경로에서 인터셉터로 사용한다 
	// addPathPatterns : 모든 경로에 대해 인터셉터를 적용한다 
	// excludePathPattenrs : /assets 경로 이하의 자원에는 인터셉터를 적용하지 않는다. 

	// ApplicationContext Event Listener
	@Bean
	public ApplicationContextEventListener applicationContextEventListener() {
		return new ApplicationContextEventListener();
	}
}