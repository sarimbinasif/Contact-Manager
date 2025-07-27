package com.scm.scm10.controller;

// import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

// import com.scm.scm10.entities.User;
// import com.scm.scm10.helpers.Helper;
import com.scm.scm10.services.UserService;

// will secure all routes starting form /user
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // uesr dashboard page

    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard"; // in user folder there is dashboard.html
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {
        // principal will give all details of logged in user
        // String username = Helper.getEmailOfLoggedInUser(authentication);

        // logger.info("User logged in: {}", username);

        // User user = userService.getUserByEmail(username);

        // System.out.println(user.getName());
        // System.out.println(user.getEmail());

        // model.addAttribute("loggedInUser", user);

        return "user/profile"; // in user folder there is profile.html
    }

}
