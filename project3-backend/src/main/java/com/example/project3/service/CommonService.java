package com.example.project3.service;

import com.example.project3.model.User;
import com.example.project3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    @Autowired
    private UserRepository userRepository;


    public boolean checkToken(String token) {
        User user = userRepository.findByToken(token);
        return user != null;
    }
}
