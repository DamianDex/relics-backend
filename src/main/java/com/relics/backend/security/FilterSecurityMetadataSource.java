package com.relics.backend.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.relics.backend.model.User;
import com.relics.backend.model.UserTypes;
import com.relics.backend.repository.AppUserRepository;

@Component
public class FilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

	@Autowired
    AppUserRepository appUserRepository;
	
	@Autowired
	LoginUtils loginUtils;
	
	List<ConfigAttribute> allUsers = new LinkedList<>();
	Map<RequestMatcher, List<ConfigAttribute>> requestMap = null;
	
	public FilterSecurityMetadataSource(){
		loadAllUsersList();
		loadRequestMap();
	}
	
	private void loadAllUsersList(){
		String s = "ROLE_";
		allUsers = SecurityConfig.createList(s + UserTypes.ManagedTypes.USER.name(), s + UserTypes.ManagedTypes.ADMIN.name());
	}
	
	private void loadRequestMap(){
		requestMap = new LinkedHashMap<>();		
		requestMap.put(new AntPathRequestMatcher("/**/admin/**"), SecurityConfig.createList("ROLE_" + UserTypes.ManagedTypes.ADMIN.name()));		
		requestMap.put(new AntPathRequestMatcher("/**/my-profile/**"), SecurityConfig.createList("ROLE_" + UserTypes.ManagedTypes.USER.name()));		
	}
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object arg0) throws IllegalArgumentException {
		FilterInvocation fi = (FilterInvocation) arg0;
		String fullRequestUrl = fi.getFullRequestUrl();
		String requestUrl = fi.getRequestUrl();
		System.out.println("Full request URL:  " + fullRequestUrl);
		
		List<ConfigAttribute> configAttributes = new ArrayList<>(0);
		
		User user = loginUtils.getLoggedUser();

		if (user != null)
			loadRequestMap();
		
		if (requestMap == null)
			loadRequestMap();
		
		AntPathMatcher apm = new AntPathMatcher();

		for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry: requestMap.entrySet()){
			RequestMatcher key = entry.getKey();
			List<ConfigAttribute> value = entry.getValue();
			AntPathRequestMatcher aprm = (AntPathRequestMatcher) key;
			boolean patternMatch = apm.match(aprm.getPattern(), requestUrl.toLowerCase());
			
			if (patternMatch){
				configAttributes = value;
			}
		}
		
		return configAttributes;
	}
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
