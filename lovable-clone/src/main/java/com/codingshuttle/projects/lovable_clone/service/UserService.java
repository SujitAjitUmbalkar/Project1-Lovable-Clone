package com.codingshuttle.projects.lovable_clone.service;

// special service for account fetching after login , editing , name changing , etc

import com.codingshuttle.projects.lovable_clone.dto.auth.UserProfileResponse;

public interface UserService
{
    UserProfileResponse me(Long userId);
}
