package com.codingshuttle.projects.lovable_clone.entity;

import com.codingshuttle.projects.lovable_clone.enums.PreviewStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

/*
 * Entity Usage:
 * Manages the live deployment of a project. It tracks the Kubernetes/Container details
 * required to serve the running application to the user via a public URL.
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Preview
{

    Long id; // PK: Unique identifier for this specific deployment instance

    Project project; // FK: The project that is currently being previewed/run

    String namespace; // Infrastructure: The Kubernetes namespace or isolation environment ID where this runs
    String podName; // Infrastructure: The specific pod/container name executing the project code
    String previewUrl; // Access: The public web address where the user can view their running app

    PreviewStatus status; // State: Current deployment condition (e.g., STARTING, RUNNING, CRASHED)

    Instant startedAt; // Lifecycle: Exact time when the container became active and reachable
    Instant terminatedAt; // Lifecycle: Exact time when the container was stopped or killed

    Instant createdAt; // Audit: When this preview record was requested/created
}