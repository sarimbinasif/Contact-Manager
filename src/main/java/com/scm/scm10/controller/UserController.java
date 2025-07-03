package com.scm.scm10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;


// will secure all routes starting form /user
@Controller
@RequestMapping("/user")
public class UserController {
    // uesr dashboard page

    @RequestMapping(value="/dashboard")
    public String userDashboard() {
        return "user/dashboard";  // in user folder there is dashboard.html
    }

    // user profile page
    @RequestMapping(value="/profile")
    public String userProfile() {
        return "user/profile";  // in user folder there is profile.html
    }

    
    // user add contact page
    
    // user view contact

    // user edit contact page


    // user delete contact page

    // user search contact page
}
