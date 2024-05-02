package com.luv2code.springboot.demosecurity.controller;

import ch.qos.logback.classic.Logger;
import com.luv2code.springboot.demosecurity.service.UserService;
import com.luv2code.springboot.demosecurity.entity.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private Logger logger = (Logger) LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    // other methods...

    @PostMapping("/processRegistration")
    public String processRegistration(@ModelAttribute("users") User user) {
        logger.info("entered processRegistration()");
        String password = user.getPassword();
        user.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(password));
        userService.saveUser(user);
        return "registration-confirmation";
    }
}
