package com.poscodx.mysite.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.dto.JsonResult;
import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		
		// modelAttribute 는 다시 jsp 에 주는 것이다 
		//vo 가 자동으로 들어가 있게 된다 !! 
	
		
		 if(result.hasErrors()) { // 에러가 있으면 다시 저 화면으로 돌아가게 한다 ! 
			 
			 
	//		  model.addAttribute("userVo", vo);
//			List<ObjectError> list = result.getAllErrors(); // 에러를 뽑아내준다 ! 
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
//			 
			 // result 를 jsp 로 보내야 한다 ! 
			 
			 Map<String,Object> map = result.getModel();
		     model.addAllAttributes(map);
		   
			return "user/join";
			 
		 }
			userService.join(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	
	// 이것 때문에 로그인을 해서 들어와야 한다 
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
	
		UserVo vo = userService.getUser(authUser.getNo());
		model.addAttribute("userVo", vo);
		
		return "user/update";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo vo) {
	
		vo.setNo(authUser.getNo());
		userService.update(vo);
		
		authUser.setName(vo.getName());
		return "redirect:/user/update";
	}
	
	@RequestMapping("/auth")
	public void auth() {
	}

	@RequestMapping("/logout")
	public void logout() {
	}
	
	
}
