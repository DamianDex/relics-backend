package com.relics.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.relics.backend.model.User;
import com.relics.backend.repository.AppUserRepository;
import com.relics.backend.security.model.LoginResult;

@Component
public class CustomAuthenticationManager implements AuthenticationManager{

	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	LoginUtils loginUtils;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		LoginResult logUser;
		User user = appUserRepository.getUser(username);
		try {
			logUser = loginUtils.getLoginResult(user, password);
		} catch (Exception e) {
			throw e;
		}
		switch (logUser) {
		case INVALID_PASSWORD:
			throw new BadCredentialsException(logUser.name());
		case NO_SUCH_USER:{
			System.out.println("NO_SUCH_USER: " + username);
			throw new BadCredentialsException(logUser.name());}
		case USER_BLOCKED:
			throw new BadCredentialsException(logUser.name());
		case USER_NOT_VERYFIED:
			throw new BadCredentialsException(logUser.name());
		case SUCCESS:
			break;
		default:
			throw new BadCredentialsException(logUser.name());
		}
	
		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	
}
