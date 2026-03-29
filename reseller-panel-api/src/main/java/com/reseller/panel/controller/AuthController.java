package com.reseller.panel.controller;


import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reseller.panel.config.JwtUtils;
import com.reseller.panel.dto.LoginRequest;
import com.reseller.panel.dto.LoginResponse;
import com.reseller.panel.dto.UserRequest;
import com.reseller.panel.dto.UserResponse;
import com.reseller.panel.response.ApiResponse;
import com.reseller.panel.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {

        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

       
        LoginResponse response = userService.getUserByUsername(userDetails.getUsername());

        
        String token = jwtUtils.generateToken(userDetails.getUsername(), response.getRole());
        response.setToken(token);

        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }
   
}
