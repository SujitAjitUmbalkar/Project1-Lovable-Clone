package com.codingshuttle.projects.lovable_clone.entity;

import com.codingshuttle.projects.lovable_clone.enums.ProjectRole;
import java.time.Instant;

/*
 * Entity Usage:
 * Represents the membership link between a User and a Project, defining their role (Editor/Viewer)
 * and managing the invitation lifecycle (pending vs. accepted).
 */
public class ProjectMember
{

    ProjectMemberId id; // PK: Composite Key (projectId + userId) preventing duplicate membership

    Project project; // Relationship: The project this member is assigned to

    User user; // Relationship: The user who is a member of the project

    ProjectRole projectRole; // Permissions: Defines access level (e.g., EDITOR, VIEWER)

    Instant invitedAt; // Workflow: Timestamp when the invitation was sent
    Instant acceptedAt; // Workflow: Timestamp when the user accepted the invite (null = pending)
}