package com.codingshuttle.projects.lovable_clone.controller;

import com.codingshuttle.projects.lovable_clone.dto.auth.AuthResponse;
import com.codingshuttle.projects.lovable_clone.dto.auth.LoginRequest;
import com.codingshuttle.projects.lovable_clone.dto.auth.SignupRequest;
import com.codingshuttle.projects.lovable_clone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(SignupRequest request)
    {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    /*
    1. signup : (/api/auth/signup) , PostMapping
        created Service , AuthService interface
        created SignupRequest DTO to fetch data from user ,
        return AuthResponse , this returns token and UserProfileResponse
        UserProfileResponse contains info about user

         2. login : (/api/auth/login) , PostMapping
        created LoginRequest DTO to fetch data from user ,
        return AuthResponse , this returns token and UserProfileResponse
     */


}
