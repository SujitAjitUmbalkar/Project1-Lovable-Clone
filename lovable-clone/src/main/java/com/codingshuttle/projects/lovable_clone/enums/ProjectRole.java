package com.codingshuttle.projects.lovable_clone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum ProjectRole
{
    EDITOR(Set.of(ProjectPermission.EDIT, ProjectPermission.DELETE, ProjectPermission.VIEW)),
    VIEWER(Set.of(ProjectPermission.VIEW)),
    OWNER(Set.of(ProjectPermission.VIEW, ProjectPermission.EDIT, ProjectPermission.DELETE));

    private final Set<ProjectPermission> permissions;
}
