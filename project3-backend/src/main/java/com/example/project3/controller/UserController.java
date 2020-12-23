package com.example.project3.controller;

import com.example.project3.domain.request.UserRequest;
import com.example.project3.domain.response.BaseResponse;
import com.example.project3.domain.response.LoginResponse;
import com.example.project3.domain.response.UserListResponse;
import com.example.project3.domain.response.UserResponse;
import com.example.project3.model.Login;
import com.example.project3.model.User;
import com.example.project3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> checkLogin(@RequestBody Login login) {
        return ResponseEntity.ok(userService.checkLogin(login));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse> logOut(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.logOut(token));
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestHeader("Authorization") String token, @RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.createUser(token,user));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.updateUser(token,user));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public ResponseEntity<UserListResponse> getAllUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getAllUser(token));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@RequestHeader("Authorization") String token,@PathVariable int id) {
        return ResponseEntity.ok(userService.deleteUser(token,id));
    }
}
