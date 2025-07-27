package com.scm.scm10.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.scm10.entities.User;
import com.scm.scm10.helpers.Helper;
import com.scm.scm10.services.UserService;

@ControllerAdvice
public class RootController {
    // add loggedInUser info
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUSerInfo(Model model, Authentication authentication) {

        if (authentication == null) {
            return;
        }
        // principal will give all details of logged in user
        String username = Helper.getEmailOfLoggedInUser(authentication);

        logger.info("User logged in: {}", username);

        User user = userService.getUserByEmail(username);

        if (user == null) {
            model.addAttribute("loggedInUser", null);
            System.out.println("User not found in DB");
        } else {
            System.out.println(user.getName());
            System.out.println(user.getEmail());

            model.addAttribute("loggedInUser", user);

        }

    }
}
