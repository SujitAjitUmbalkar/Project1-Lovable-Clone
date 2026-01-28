package com.codingshuttle.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.Instant;

/*
 * UsageLog Entity Usage:
 * Acts as the "meter" for the application. It records every expensive interaction
 * (like AI code generation) to enforce plan limits (e.g., 50 messages/day),
 * calculate costs, and debug prompt performance.
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsageLog
{

    Long id; // PK: Unique identifier for this specific log entry

    User user; // FK: The user who triggered the action (for personal usage tracking)
    Project project; // FK: The project where this occurred (for project-level analytics)

    String action; // Type: The specific operation performed (e.g., "GENERATE_CODE", "FIX_BUG", "EXPLAIN")

    Integer tokensUsed; // Cost: Total LLM tokens (input + output) consumed by this request
    Integer durationMs; // Performance: How long the AI took to respond (latency tracking)

    String metaData; // Debug: JSON string storing extra context like { "model": "gpt-4", "prompt_length": 120 }

    Instant createdAt; // Audit: Exact timestamp when this usage occurred (used for daily/monthly limit resets)
}