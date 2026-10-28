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

    private boolean hasPermission(Long projectId, ProjectPermission permission)
    {
        Long userId = authUtils.getCurrentUserId();

        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,userId)
                .map(role -> role.getPermissions().contains(permission))
                .orElse(false);
    }

    public boolean canViewProject(Long projectId)
    {
        return hasPermission(projectId, ProjectPermission.VIEW);
    }

    public boolean canEditProject(Long projectId)
    {
        return hasPermission(projectId, ProjectPermission.EDIT);
    }

    public boolean canDeleteProject(Long projectId)
    {
        return hasPermission(projectId, ProjectPermission.DELETE);
    }

    public boolean canViewMembers(Long projectId)
    {
        return hasPermission(projectId, ProjectPermission.VIEW_MEMBERS);
    }

    public boolean canManageMembers(Long projectId)
    {
        return hasPermission(projectId,ProjectPermission.MANAGE_MEMBERS);
    }


}
