package com.poscodx.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.GuestbookVo;
import com.poscodx.mysite.vo.UserVo;


@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String showingGuestbook(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list",list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(GuestbookVo vo) {
		    guestbookService.addContents(vo);
			return  "redirect:/guestbook";
		}
	
	@RequestMapping(value="/deleteform/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/deleteform";
	}

    @RequestMapping(value="/deleteform/{no}", method=RequestMethod.POST)
    public String delete(@PathVariable("no") Long no, @RequestParam("password") String password) {
		guestbookService.deleteContents(no, password);
		return "redirect:/guestbook"; 
	}
        

	
}