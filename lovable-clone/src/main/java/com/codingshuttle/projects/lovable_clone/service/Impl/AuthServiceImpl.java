package com.codingshuttle.projects.lovable_clone.service.Impl;

import com.codingshuttle.projects.lovable_clone.dto.auth.AuthResponse;
import com.codingshuttle.projects.lovable_clone.dto.auth.LoginRequest;
import com.codingshuttle.projects.lovable_clone.dto.auth.SignupRequest;
import com.codingshuttle.projects.lovable_clone.entity.User;
import com.codingshuttle.projects.lovable_clone.error.BadRequestException;
import com.codingshuttle.projects.lovable_clone.mapper.UserMapper;
import com.codingshuttle.projects.lovable_clone.repository.UserRepository;
import com.codingshuttle.projects.lovable_clone.security.AuthUtils;
import com.codingshuttle.projects.lovable_clone.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService
{
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthUtils authUtils;
    AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest request)
    {
        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BadRequestException(" User already exists with username " + request.username());
        });

        User user = userMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);

        String token = authUtils.generateAccessToken(savedUser);

        return new AuthResponse(token, userMapper.toUserProfileResponse(savedUser));
    }

    @Override
    public AuthResponse login(LoginRequest request)
    {
     Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

     User user = (User) authentication.getPrincipal();

     String token = authUtils.generateAccessToken(user);

        return new AuthResponse(token, userMapper.toUserProfileResponse(user));

    }
}
