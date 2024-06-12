package com.poscodx.mysite.exception;

import java.io.StringWriter;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 모든 controller 에서 발생하는 예외를 감지한다 
@ControllerAdvice
public class GlobalExceptionHandler {

	
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
	public String handler(Exception e, Model model) {
		
    	StringWriter errors = new StringWriter();
    	e.printStackTrace(new PrintWriter(errors));
        logger.error(errors.toString());
    
    	//2. 사과 
    	model.addAttribute("error", errors);
    	// 500 페이지의 에러 -> tomcat 까지 나왔구나. 잘 일어나지 않게 된다. 
    	// exception 은 스프링 어플리케이션과 관련된 예외이구나 -> 컨테이너에서의 에러 
    	
    	return  "errors/exception";
    	
    	// 3. 종료 
	}
}
