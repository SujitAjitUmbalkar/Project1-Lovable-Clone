package com.codingshuttle.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

/*
 * Entity Usage:
 * Represents a specific file (code, image, config) inside a user's project.
 * It maps the logical file path (what the user sees) to the physical object storage key (where data is actually saved).
 */

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectFile
{

    Long id; // PK: Unique identifier for the file record

    Project project; // FK: The project that contains this file

    String path; // Structure: The logical path and filename relative to project root (e.g., "src/components/App.tsx")

    String minioObjectKey; // Storage: The unique UUID/key used to retrieve the actual file content from MinIO/S3

    Instant createdAt; // Audit: When this file was first created
    Instant updatedAt; // Audit: When the file content was last modified

    User createdBy; // Audit: The user who originally created this file
    User updatedBy; // Audit: The user who made the most recent change
}