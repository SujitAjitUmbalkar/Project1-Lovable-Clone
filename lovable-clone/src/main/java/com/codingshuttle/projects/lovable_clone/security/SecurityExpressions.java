package com.codingshuttle.projects.lovable_clone.security;

import com.codingshuttle.projects.lovable_clone.enums.ProjectPermission;
import com.codingshuttle.projects.lovable_clone.enums.ProjectRole;
import com.codingshuttle.projects.lovable_clone.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("security")
public class SecurityExpressions
{
    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtils  authUtils;

    public boolean canViewProject(Long projectId)
    {
        Long userId = authUtils.getCurrentUserId();

        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,userId)
                .map(role -> role.getPermissions().contains(ProjectPermission.VIEW))
                .orElse(false);
    }

    public boolean canEditProject(Long projectId)
    {
        Long userId = authUtils.getCurrentUserId();

        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,userId)
                .map(role -> role.getPermissions().contains(ProjectPermission.EDIT))
                .orElse(false);
    }

}
