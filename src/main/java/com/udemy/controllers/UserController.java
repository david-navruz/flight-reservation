package com.udemy.controllers;

import com.udemy.repositories.UserRepository;
import com.udemy.entities.User;
import com.udemy.services.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping(value = "/showReg", method = { RequestMethod.GET, RequestMethod.POST})
    public String showRegistration() {
        return "login/registerUser";
    }


    @RequestMapping(value = "/registerUser", method = { RequestMethod.GET, RequestMethod.POST})
    public String register(@ModelAttribute("user") User user) {
        LOGGER.info("inside register() " + user);
        user.setPassword(encoder.encode(user.getPassword())); // encoding the user's password
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
        boolean loginResponse = securityService.login(email, password);
        LOGGER.info("Inside login() and the email is: " + email);
        User user = userRepository.findByEmail(email);
        if (loginResponse) {
            return "findFlights";
        } else {
            modelMap.addAttribute("msg", "Invalid username or password. Please try again");
        }
        return "login/login";
    }


}
