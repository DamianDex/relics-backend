package com.relics.backend.controller;

import com.relics.backend.model.AppUser;
import com.relics.backend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @PostMapping("/user")
    public void createNewUser(@Valid @RequestBody AppUser user) {
        appUserRepository.save(user);
    }

    @GetMapping("/user")
    @ResponseBody
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

}
