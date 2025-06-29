package com.scm.scm10.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.scm.scm10.entities.User;
import com.scm.scm10.forms.UserForm;
import com.scm.scm10.helpers.Message;
import com.scm.scm10.helpers.MessageType;
import com.scm.scm10.services.UserService;

import ch.qos.logback.core.joran.spi.HttpUtil.RequestMethod;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class PageController {

    @Autowired
    private UserService userService;

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
//    @PostMapping("/do-signup")
// public String processSignup(@ModelAttribute UserForm userForm) {
//     System.out.println("in process signup function");
//     System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//     // fetch form data
//     System.out.println(userForm);
    
//     // validate form data
//     // save to DB
//     User user  = User.builder()
//     .name(userForm.getName())
//     .email(userForm.getEmail())
//     .about(userForm.getAbout())
//     .phoneNumber(userForm.getPhoneNumber())
//     .profilePic(profilePic: "src\main\resources\static\images\default-profile-pic.jpg")
//     .build();
//     User savedUser = userService.saveUser(userForm);


//     // redirect to login page
//     // msg "registration successfull"
//     // redirect to login
//     return "redirect:/login"; // or return view name like "signup-success"
// }

    @PostMapping("/do-signup")
public String processSignup(@ModelAttribute UserForm userForm,HttpSession session,
                            @RequestParam("profilePic") MultipartFile file) {
    System.out.println("in process signup function");
    // System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    // System.out.println(userForm);

    String profilePicPath;

                                
    if (!file.isEmpty()) {
        try {
            // Absolute path to the upload directory inside static folder
            String uploadDir = new File("src/main/resources/static/images/uploads").getAbsolutePath();

            // Ensure directory exists
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // Save file
            File saveFile = new File(uploadDir + File.separator + file.getOriginalFilename());
            file.transferTo(saveFile);

            // Set relative path to be stored in DB (used in Thymeleaf etc.)
            profilePicPath = "/images/uploads/" + file.getOriginalFilename();

        } catch (IOException e) {
            e.printStackTrace();
            profilePicPath = "/images/default-profile-pic.jpg"; // fallback
        }
    } else {
        profilePicPath = "/images/default-profile-pic.jpg";
    }
    

    // User user = User.builder()
    //         .name(userForm.getName())
    //         .email(userForm.getEmail())
    //         .about(userForm.getAbout())
    //         .password(userForm.getPassword())
    //         .phoneNumber(userForm.getPhoneNumber())
    //         .profilePic(profilePicPath)
    //         .build();

   User user = new User();
user.setName(userForm.getName());
user.setEmail(userForm.getEmail());
user.setPassword(userForm.getPassword());
user.setAbout(userForm.getAbout());
user.setPhoneNumber(userForm.getPhoneNumber());
user.setProfilePic(profilePicPath);

    User savedUser = userService.saveUser(user);
    System.out.println(savedUser);
    Message message = Message.builder().content("Resgistration Succesful").type(MessageType.green).build();

    session.setAttribute("message", message);

    return "redirect:/signup"; // or wherever you want to go after signup
}




}
