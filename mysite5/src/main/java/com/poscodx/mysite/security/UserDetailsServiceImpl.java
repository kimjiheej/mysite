package com.poscodx.mysite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.poscodx.mysite.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	// 사용자 이름을 사용하여 데이터베이스에서 사용자 정보를 검색하고 UserDetails 객체로 반환한다. 
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail2(username);
	}
}
