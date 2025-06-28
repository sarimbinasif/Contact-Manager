package com.scm.scm10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm10.forms.UserForm;

import ch.qos.logback.core.joran.spi.HttpUtil.RequestMethod;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class PageController {
    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("in home page controller");

        //sending data to view
        model.addAttribute("githubrepo", "https://github.com/sarimbinasif");

        return "home";
    }


     @RequestMapping("/about")
    public String about(Model model){
        System.out.println("in about page controller");

        //sending data to view
        // model.addAttribute("githubrepo", "https://github.com/sarimbinasif");

        return "about";
    }


     @RequestMapping("/services")
    public String services(Model model){
        System.out.println("in services page controller");

        //sending data to view
        // model.addAttribute("githubrepo", "https://github.com/sarimbinasif");

        return "services";
    }

     @RequestMapping("/contact")
    public String contact(Model model){
        System.out.println("in contact page controller");

        //sending data to view
        // model.addAttribute("githubrepo", "https://github.com/sarimbinasif");

        return "contact";
    }


         @RequestMapping("/login")
    public String login(Model model){
        System.out.println("in login page controller");

        //sending data to view
        // model.addAttribute("githubrepo", "https://github.com/sarimbinasif");

        return "login";
    }

    // signup
    @GetMapping("/signup")
    public String signup(Model model){
        System.out.println("in signup page controller");

        //sending default data to view(signup.html)
        UserForm userform = new UserForm();
        userform.setName("Sarim Asif");
        userform.setEmail("abc@example.com");
        userform.setPassword("25@25");
        userform.setPhoneNumber("03312628971");
        userform.setAbout("Aspiring full-stack developer with a passion for building web applications.");
        model.addAttribute("userForm", userform);
        

        // model.addAttribute("githubrepo", "https://github.com/sarimbinasif");


        return "signup";

    }


    // process signup
   @PostMapping("/do-signup")
public String processSignup(@ModelAttribute UserForm userForm) {
    System.out.println("in process signup function");
    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    // fetch form data
    System.out.println(userForm);
    
    // validate form data
    // save to DB
    // redirect to login page
    // msg "registration successfull"
    // redirect to login
    return "redirect:/login"; // or return view name like "signup-success"
}
}
