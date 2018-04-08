package com.relics.backend.security;

import com.relics.backend.model.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.relics.backend.model.ApplicationUser;
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
		ApplicationUser applicationUser = appUserRepository.getUser(username);
		try {
			logUser = loginUtils.getLoginResult(applicationUser, password);
		} catch (Exception e) {
			throw e;
		}
		switch (logUser) {
		case INVALID_PASSWORD:
			throw new BadCredentialsException(Messages.INVALID_PASSWORD.getDescription());
		case NO_SUCH_USER:
			throw new UsernameNotFoundException(Messages.NO_SUCH_USER.getDescription());
		case USER_NOT_VERIFIED:
			throw new BadCredentialsException(Messages.USER_NOT_VERIFIED.getDescription());
		case SUCCESS:
			break;
		default:
			throw new BadCredentialsException(logUser.name());
		}
	
		return new UsernamePasswordAuthenticationToken(applicationUser, password, applicationUser.getAuthorities());
	}

	
}
