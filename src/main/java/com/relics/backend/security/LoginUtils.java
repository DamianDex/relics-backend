package com.relics.backend.security;

import com.relics.backend.model.User;
import com.relics.backend.security.model.LoginResult;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class LoginUtils {
	
	public LoginResult getLoginResult(User user, String password) {
		if (user == null) {
			return LoginResult.NO_SUCH_USER;
		} else {
			if (!user.getPassword().equals(user.getPassword())) {
				return LoginResult.INVALID_PASSWORD;
			} else {
				return LoginResult.SUCCESS;
			}
		}
	}
	
	public User getLoggedUser() {
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user instanceof User) {
			return (User) user;
		}
		return null;		
	}

}
