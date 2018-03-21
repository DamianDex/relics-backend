package com.relics.backend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.relics.backend.model.ErrorMessages;
import com.relics.backend.repository.AppUserRepository;
import com.relics.backend.security.LoginUtils;
import com.relics.backend.security.model.RegistrationBean;

@RestController
@RequestMapping("/api")
public class AppUserController implements BasicController {

    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private LoginUtils loginUtils;

    @PostMapping("/user/add")
    public ErrorMessages createNewUser(@RequestBody RegistrationBean user) {
    	boolean userExists = appUserRepository.userExists(user.getUsername());
    	if (!userExists) {
    		try {
            	appUserRepository.addUser(user.getUsername(), loginUtils.passwordEncoder().encode(user.getPassword()), UUID.randomUUID().toString());
    		} catch (Exception e) {
    			return ErrorMessages.INNER_SERVER_ERROR;
    		}
        	return ErrorMessages.USER_CREATED;
    	} else {
			return ErrorMessages.USERNAME_CONFLICT;
    	}
    }
    
    @PostMapping("/my-profile")
    public String getProfileInfo() {
    	System.out.println(loginUtils.getLoggedUser().toString());
    	return "I'm logged!";
    }


//    @GetMapping("/user")
//    @ResponseBody
//    public List<AppUser> getAllUsers() {
//        return appUserRepository.findAll();
//    }

}
