package com.codingshuttle.projects.lovable_clone.service;

import com.codingshuttle.projects.lovable_clone.dto.project.ProjectResponse;
import com.codingshuttle.projects.lovable_clone.dto.project.ProjectSummuryResponse;

import java.util.List;

public interface ProjectService
{
    List<ProjectSummuryResponse> getUserProjects(Long userId);

    ProjectResponse getProjectById(Long id, Long userId);
}
