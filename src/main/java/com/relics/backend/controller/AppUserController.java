package com.relics.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.relics.backend.model.User;
import com.relics.backend.repository.AppUserRepository;

@RestController
@RequestMapping("/api")
public class AppUserController implements BasicController {

    @Autowired
    AppUserRepository appUserRepository;

    @PostMapping("/user/add")
    public void createNewUser(@RequestBody User user) {
    	appUserRepository.addUser(user.getUsername(), user.getPassword());
    }
    
    @PostMapping("/my-profile")
    public String getProfileInfo() {
    	return "I'm logged!";
    }


//    @GetMapping("/user")
//    @ResponseBody
//    public List<AppUser> getAllUsers() {
//        return appUserRepository.findAll();
//    }

}
