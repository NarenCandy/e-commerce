package com.example.ecommerce_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_backend.model.User;
import com.example.ecommerce_backend.repository.UserRepository;
import com.example.ecommerce_backend.util.JwtUtil;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public User register(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //login endpoint 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData){
        User user = userRepository.findByUsername(loginData.getUsername());
        if (user != null && passwordEncoder.matches(loginData.getPassword(),user.getPassword())) {
           String token = jwtUtil.generateToken(user.getUsername());
           return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Invalid username or password");
    }

    }
}
