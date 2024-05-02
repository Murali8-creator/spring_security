package com.luv2code.springboot.demosecurity.service;


import com.luv2code.springboot.demosecurity.entity.User;
import com.luv2code.springboot.demosecurity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    public void saveUser(User user) {
        userRepository.save(user);
        logger.info("entered userservice");
    }
}
