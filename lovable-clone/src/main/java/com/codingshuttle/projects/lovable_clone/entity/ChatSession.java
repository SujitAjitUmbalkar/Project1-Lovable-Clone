package com.codingshuttle.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

/*
 * Entity Usage:
 * Represents a conversation thread (like a specific "chat room") for a project.
 * It groups messages together and helps the AI maintain context (memory)
 * relevant to the specific problem being discussed in that session.
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatSession
{

    // Note: You likely missed 'Long id;' here based on the ER Diagram.
    Long id; // PK: Unique identifier for the chat session

    Project project; // FK: The specific project this conversation is referencing (scopes the AI's context)

    User user; // FK: The user who created this chat session

    String title; // UI: A short summary of the conversation (e.g., "Fixing the Navbar") for the sidebar list

    Instant createdAt; // Audit: When this conversation started
    Instant updatedAt; // Audit: When the last message was sent or the title was changed

    Instant deletedAt; // Audit: Soft delete timestamp; allows users to clear history without deleting data
}