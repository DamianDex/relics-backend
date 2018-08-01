package com.relics.backend.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomDecisionManager implements AccessDecisionManager{
	
	@Override
	public void decide(Authentication auth, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		if (configAttributes == null)
			return;
		
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()){
			ConfigAttribute configAttribute = iterator.next();
			String neededPermission = configAttribute.getAttribute();
			System.out.println("Needed permision is: " + neededPermission);
			for (GrantedAuthority ga: auth.getAuthorities()){
				if (neededPermission.equals(ga.getAuthority())){
					System.out.println("Authorization granted");
					return;
				}
			}
		}
		throw new AccessDeniedException("You don't have permission to access");
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
