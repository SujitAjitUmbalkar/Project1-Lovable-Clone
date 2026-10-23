package com.codingshuttle.projects.lovable_clone.dto.member;

import com.codingshuttle.projects.lovable_clone.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse
        (
                Long userId ,
                String name ,
                String username,
                ProjectRole projectRole,
                Instant invitedAt
        )
{
}
