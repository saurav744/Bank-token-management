package com.saurav.bankingapp.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.saurav.bankingapp.exceptions.UserNotFoundException;
import com.saurav.bankingapp.service.UserService;
;

@Service
public class BankingAppUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		com.saurav.bankingapp.model.User activeUserInfo;
		UserDetails userDetails = null;
		try {
			activeUserInfo = userService.get(userName);

			GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getType().toString());
			userDetails = (UserDetails) new User(activeUserInfo.getPhone(),
					activeUserInfo.getPassword(), Arrays.asList(authority));
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userDetails;
	}

}
