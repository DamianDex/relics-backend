package com.relics.backend.security;

import com.relics.backend.model.ApplicationUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.relics.backend.security.model.LoginResult;


@Component
public class LoginUtils {
	
	public LoginResult getLoginResult(ApplicationUser applicationUser, String password) {
		if (applicationUser == null) {
			return LoginResult.NO_SUCH_USER;
		} else {
			if (passwordEncoder().matches(password, applicationUser.getPassword())) {
				return LoginResult.INVALID_PASSWORD;
			} else {
				return LoginResult.SUCCESS;
			}
		}
	}
	
	public ApplicationUser getLoggedUser() {
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user instanceof ApplicationUser) {
			return (ApplicationUser) user;
		}
		return null;		
	}
	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
