package com.example.project3.helper;

import com.example.project3.model.User;
import com.example.project3.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public class JwtToken {
    private String secret = RandomStringUtils.randomAlphanumeric(10);



    //generate token for user
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getEmail());
    }

    public String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).signWith(SignatureAlgorithm.HS512, secret).compact();
    }



}
