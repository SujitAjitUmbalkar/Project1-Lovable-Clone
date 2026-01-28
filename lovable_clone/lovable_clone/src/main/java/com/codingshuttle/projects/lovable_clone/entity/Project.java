package com.codingshuttle.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

/*
 * Entity Usage:
 * Represents a coding workspace or "app" created by a user. This is the central
 * container for all files, chat sessions, and deployments associated with a specific idea.
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project
{

    Long id; // PK: Unique identifier for the project

    String name; // Display: The name of the project (e.g., "My ToDo App")

    User owner; // FK: The creator and primary admin of this project

    Boolean isPublic = false; // Visibility: Determines if the project can be viewed/cloned by other users

    Instant createdAt; // Audit: Timestamp when the project was created
    Instant updatedAt; // Audit: Timestamp when the project details were last modified
    Instant deletedAt; // Audit: Soft delete timestamp; if set, project is in "Trash" but not wiped
}