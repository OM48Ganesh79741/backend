package com.qsp.roleAccessed.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.qsp.roleAccessed.entity.User;
import com.qsp.roleAccessed.repo.UserRepository;
import com.qsp.roleAccessed.securityConfigg.jwtToken;
import com.qsp.roleAccessed.userDe.CustomUserDetailsService;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private jwtToken jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // default role
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }

        userRepo.save(user);

        return "User Registered Successfully";
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(user.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        return Map.of("token", token,
        		"role", user.getRole() != null ? user.getRole() : "USER"
        		 );
    }
}
