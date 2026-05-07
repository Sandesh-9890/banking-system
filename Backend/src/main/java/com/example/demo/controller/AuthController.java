package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Signup API
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {

        // default role
        user.setRole("ROLE_USER");

        userRepo.save(user);

        return "Signup successful";
    }

    // ✅ Login API
    @PostMapping("/login")
    public Object login(@RequestBody User user) {

        User existingUser =
                userRepo.findByUsername(
                        user.getUsername()
                ).orElse(null);

        if (existingUser == null) {

            return "User not found";
        }

        if (!existingUser.getPassword()
                .equals(user.getPassword())) {

            return "Invalid credentials";
        }

        // ✅ Generate JWT with role
        String token =
                jwtUtil.generateToken(
                        existingUser.getUsername(),
                        existingUser.getRole()
                );

        return token;
    }
}