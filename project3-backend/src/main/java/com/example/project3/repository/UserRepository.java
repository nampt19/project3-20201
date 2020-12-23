package com.example.project3.repository;

import com.example.project3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findByToken(String token);
    User findByPhone(String phone);
}
