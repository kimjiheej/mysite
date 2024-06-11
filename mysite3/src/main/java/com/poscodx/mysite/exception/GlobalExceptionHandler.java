package com.poscodx.mysite.exception;

import java.io.StringWriter;
import java.io.PrintWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 모든 controller 에서 발생하는 예외를 감지한다 
@ControllerAdvice
public class GlobalExceptionHandler {

	 // mysite 에서 발생하는 exception 은 다 여기로 모이게 된다 
	
	// 모든 예외를 다 처리한다는 의미이다 !! 
    @ExceptionHandler(Exception.class)
	public String handler(Exception e, Model model) {
		
    	//1. 로깅(logging) 
    
    	
    	StringWriter errors = new StringWriter();
    	e.printStackTrace(new PrintWriter(errors));
    	System.out.println(errors.toString());
    	
    	//2. 사과 
    	model.addAttribute("error", errors);
    	// 500 페이지의 에러 -> tomcat 까지 나왔구나. 잘 일어나지 않게 된다. 
    	// exception 은 스프링 어플리케이션과 관련된 예외이구나 -> 컨테이너에서의 에러 
    	
    	return  "errors/exception";
    	
    	// 3. 종료 
	}
}
