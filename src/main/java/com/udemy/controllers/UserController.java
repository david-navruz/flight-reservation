package com.udemy.controllers;

import com.udemy.repositories.UserRepository;
import com.udemy.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "/showReg", method = { RequestMethod.GET, RequestMethod.POST})
    public String showRegistration() {
        return "login/registerUser";
    }


    @RequestMapping(value = "/registerUser", method = { RequestMethod.GET, RequestMethod.POST})
    public String register(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "login/login";
    }


    @RequestMapping(value = "/showLogin", method = { RequestMethod.GET, RequestMethod.POST})
    public String showLoginPage() {
        return "login/login";
    }


    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST})
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        ModelMap modelMap) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equalsIgnoreCase(password)) {
            return "findFlights";
        } else {
            modelMap.addAttribute("msg", "Invalid username or password. Please try again");
        }
        return "login/login";
    }


}
