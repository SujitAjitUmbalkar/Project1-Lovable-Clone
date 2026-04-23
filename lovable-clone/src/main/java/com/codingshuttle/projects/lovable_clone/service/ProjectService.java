package com.codingshuttle.projects.lovable_clone.service;

import com.codingshuttle.projects.lovable_clone.dto.project.ProjectSummuryResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectService
{
    List<ProjectSummuryResponse> getUserProjects(Long userId);
}
