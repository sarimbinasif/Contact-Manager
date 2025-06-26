package com.scm.scm10;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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


         @RequestMapping("/signup")
    public String signup(Model model){
        System.out.println("in signup page controller");

        //sending data to view
        // model.addAttribute("githubrepo", "https://github.com/sarimbinasif");

        return "signup";

    }
}
