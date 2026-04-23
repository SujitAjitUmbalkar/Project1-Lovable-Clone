package com.codingshuttle.projects.lovable_clone.controller;

import com.codingshuttle.projects.lovable_clone.dto.auth.AuthResponse;
import com.codingshuttle.projects.lovable_clone.dto.auth.LoginRequest;
import com.codingshuttle.projects.lovable_clone.dto.auth.SignupRequest;
import com.codingshuttle.projects.lovable_clone.dto.auth.UserProfileResponse;
import com.codingshuttle.projects.lovable_clone.service.AuthService;
import com.codingshuttle.projects.lovable_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController
{
    private final AuthService authService;
    private final UserService userService;

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

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> me()     // no need to use AuthResponse , bcoz no need for token
    {
        Long userId = 1L;   // used while Spring security

        return ResponseEntity.ok(userService.me(userId));

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

3. me : (/api/auth/me) , GetMapping
    no request DTO required (data comes from logged-in user context later)
    return UserProfileResponse ( no need for jwt so AuthResponse is saperated )
    → usereSerivce is created to provide special service on account editing
    → userId will come from Spring Security (JWT / Authentication)
    currently:
    → using dummy userId = 1L (for testing)
*/



}
