package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.repository.UserRepository;
import com.poscodx.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getGuestbookList() {
	List<GuestbookVo> list = 	guestbookRepository.findAll();
		return list;
	}
	
    public void deleteContents(Long no, String password) {
    	guestbookRepository.delete(String.valueOf(no), password);
    }
    
    public void addContents(GuestbookVo vo) {
    	guestbookRepository.insert(vo);
    }
	
}
