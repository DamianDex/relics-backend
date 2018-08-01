package com.relics.backend.controller;

import com.relics.backend.model.ApplicationUser;
import com.relics.backend.model.Messages;
import com.relics.backend.model.UserTypes;
import com.relics.backend.repository.AppUserRepository;
import com.relics.backend.repository.UserTypesRepository;
import com.relics.backend.security.LoginUtils;
import com.relics.backend.security.model.RegistrationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/admin")
    public Boolean isAdmin(){
        return true;
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

}
