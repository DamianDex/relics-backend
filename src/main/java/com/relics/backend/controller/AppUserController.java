package com.relics.backend.controller;

import com.relics.backend.model.ApplicationUser;
import com.relics.backend.model.Messages;
import com.relics.backend.model.UserTypes;
import com.relics.backend.repository.AppUserRepository;
import com.relics.backend.repository.UserTypesRepository;
import com.relics.backend.security.LoginUtils;
import com.relics.backend.security.model.RegistrationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:3000")
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

    @GetMapping("/user/logged")
    public Boolean isLogged() {
        ApplicationUser loggedUser = loginUtils.getLoggedUser();
        if (loggedUser != null)
            return true;
        return false;
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

    @GetMapping("/my-profile")
    @ResponseBody
    public ApplicationUser getApplicationUserById(){
        Long userId = loginUtils.getLoggedUser().getId();
        ApplicationUser applicationUser = appUserRepository.getApplicationUserById(userId);
        return appUserRepository.getApplicationUserById(userId);
    }


    @PutMapping("/my-profile/{userId}")
    public ResponseEntity<ApplicationUser> updateUser(@Valid @RequestBody ApplicationUser updateApplicationUser){
        Long userId = loginUtils.getLoggedUser().getId();
        ApplicationUser applicationUser = appUserRepository.getApplicationUserById(userId);
        applicationUser.setUsername(updateApplicationUser.getUsername());
        applicationUser.setFirstName(updateApplicationUser.getFirstName());
        applicationUser.setLastName(updateApplicationUser.getLastName());
        applicationUser.setEmail(updateApplicationUser.getEmail());
        applicationUser.setProfileImage(updateApplicationUser.getProfileImage());
        appUserRepository.save(updateApplicationUser);
        return  ResponseEntity.ok(updateApplicationUser);
    }
}
