package com.codingshuttle.projects.lovable_clone.entity;

/*
ProjectMemberId is a composite key combining projectId and userId to uniquely identify a membership
 and prevent duplicate user-project associations
 */

public class ProjectMemberId
{
    Long projectId; // Part of PK: Identifies which project this membership belongs to
    Long userId;    // Part of PK: Identifies which user is being granted access
}
