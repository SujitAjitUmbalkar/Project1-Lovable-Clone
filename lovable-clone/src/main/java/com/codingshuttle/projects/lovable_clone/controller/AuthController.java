package com.codingshuttle.projects.lovable_clone.controller;

import com.codingshuttle.projects.lovable_clone.dto.auth.AuthResponse;
import com.codingshuttle.projects.lovable_clone.dto.auth.LoginRequest;
import com.codingshuttle.projects.lovable_clone.dto.auth.SignupRequest;
import com.codingshuttle.projects.lovable_clone.dto.auth.UserProfileResponse;
import com.codingshuttle.projects.lovable_clone.service.AuthService;
import com.codingshuttle.projects.lovable_clone.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController
{
     AuthService authService;
     UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request)
    {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> me()
    {
        Long userId = 1L;   // used while Spring security
        return ResponseEntity.ok(userService.me(userId));
    }

}
