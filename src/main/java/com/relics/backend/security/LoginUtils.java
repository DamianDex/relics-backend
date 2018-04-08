package com.relics.backend.security;

import com.relics.backend.model.ApplicationUser;
import com.relics.backend.security.model.RegistrationBean;
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
				return LoginResult.SUCCESS;
			} else {
				return LoginResult.INVALID_PASSWORD;
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

	public ApplicationUser encodeUserPassword(ApplicationUser user){
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		return user;
	}

	public RegistrationBean encodeUserPassword(RegistrationBean user){
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		return user;
	}
	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
