package com.codingshuttle.projects.lovable_clone.service;

import com.codingshuttle.projects.lovable_clone.dto.project.ProjectRequest;
import com.codingshuttle.projects.lovable_clone.dto.project.ProjectResponse;
import com.codingshuttle.projects.lovable_clone.dto.project.ProjectSummuryResponse;

import java.util.List;

public interface ProjectService
{
    List<ProjectSummuryResponse> getUserProjects(Long userId);

    ProjectResponse getProjectById(Long id, Long userId);

    ProjectResponse createProject(Long userId, ProjectRequest request);

    ProjectResponse updateProject(Long id, ProjectRequest request, Long userId);

    void softDelete(Long id, Long userId);
}
