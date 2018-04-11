package com.relics.backend.controller;

import com.relics.backend.model.ApplicationUser;
import com.relics.backend.model.UserTypes;
import com.relics.backend.repository.UserTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.relics.backend.model.Messages;
import com.relics.backend.repository.AppUserRepository;
import com.relics.backend.security.LoginUtils;

import com.relics.backend.security.model.RegistrationBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class AppUserController implements BasicController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserTypesRepository userTypesRepository;
    
    @Autowired
    private LoginUtils loginUtils;

    @PostMapping("/user/add")
    public Messages createNewUser(@RequestBody RegistrationBean user) {
    	boolean userExists = appUserRepository.userExists(user.getUsername());
    	if (!userExists) {
    		try {
                user = loginUtils.encodeUserPassword(user);
            	UserTypes uType = userTypesRepository.getUserTypeByCode(UserTypes.ManagedTypes.USER.name());
                ApplicationUser appUser = new ApplicationUser(user, uType);
				appUserRepository.save(appUser);
    		} catch (Exception e) {
    		    System.out.println(e);
    			return Messages.INNER_SERVER_ERROR;
    		}
        	return Messages.USER_CREATED;
    	} else {
			return Messages.USERNAME_CONFLICT;
    	}
    }
    
    @PostMapping("/my-profile")
    public String getProfileInfo() {
    	System.out.println(loginUtils.getLoggedUser().toString());
    	return "I'm logged!";
    }

    @GetMapping("/logout")
    public Messages logout(HttpServletRequest request) {
		SecurityContextHolder.clearContext();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return Messages.LOGGED_OUT;
    }

}
