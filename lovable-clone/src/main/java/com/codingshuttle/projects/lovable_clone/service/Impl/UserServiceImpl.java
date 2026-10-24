package com.codingshuttle.projects.lovable_clone.service.Impl;

import com.codingshuttle.projects.lovable_clone.dto.auth.UserProfileResponse;
import com.codingshuttle.projects.lovable_clone.error.ResourceNotFoundException;
import com.codingshuttle.projects.lovable_clone.repository.UserRepository;
import com.codingshuttle.projects.lovable_clone.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService , UserDetailsService
{
    UserRepository userRepository;

    @Override
    public UserProfileResponse me(Long userId) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        return userRepository.findByUsername(username).orElseThrow(()->
                new ResourceNotFoundException("User Not Found With Username :  ", username));
    }
}
