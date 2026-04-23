package com.codingshuttle.projects.lovable_clone.controller;

import com.codingshuttle.projects.lovable_clone.dto.project.ProjectSummuryResponse;
import com.codingshuttle.projects.lovable_clone.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController
{
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectSummuryResponse>> getUserProjects()
    {
        Long  userId = 1L;          // will use in spring security
        return  ResponseEntity.ok(projectService.getUserProjects(userId));
    }

}
