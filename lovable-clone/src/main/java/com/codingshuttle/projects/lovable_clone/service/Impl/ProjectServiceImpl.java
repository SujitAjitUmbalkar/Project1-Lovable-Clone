package com.codingshuttle.projects.lovable_clone.service.Impl;

import com.codingshuttle.projects.lovable_clone.dto.project.ProjectRequest;
import com.codingshuttle.projects.lovable_clone.dto.project.ProjectResponse;
import com.codingshuttle.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.codingshuttle.projects.lovable_clone.entity.Project;
import com.codingshuttle.projects.lovable_clone.entity.User;
import com.codingshuttle.projects.lovable_clone.mapper.ProjectMapper;
import com.codingshuttle.projects.lovable_clone.repository.ProjectRepository;
import com.codingshuttle.projects.lovable_clone.repository.UserRepository;
import com.codingshuttle.projects.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@Transactional
public class ProjectServiceImpl implements ProjectService
{
    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;

    @Override
    public ProjectResponse createProject(Long userId, ProjectRequest request)
    {
        User owner = userRepository.findById(userId).orElseThrow();

        Project project = Project.builder()
                .name(request.name())
                .owner(owner)
                .isPublic(false)
                .build();

         project = projectRepository.save(project);

         return projectMapper.toProjectResponse(project);

    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId)
    {
//        return projectRepository.getAllProjectsAccessibleByUser(userId)
//                .stream()
//                .map(project -> projectMapper.toProjectSummaryResponse(project))
//                .collect(Collectors.toList());

//        OR YOU CAN USE MAPSTRUCT DIRECTLY

        return
                projectMapper.toListOfProjectSummaryResponse(projectRepository.getAllProjectsAccessibleByUser(userId));
    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId)
    {
        Project project = getAccessibleProjectById(userId, id);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId)
    {
        Project project = getAccessibleProjectById(userId, id);

        project.setName(request.name());

        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long id, Long userId)
    {
        Project project = getAccessibleProjectById(userId, id);

        if (! project.getOwner().getId().equals(userId))
        {
            throw  new RuntimeException("You are not allowed to delete this project");
        }
        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    ///  INTERNAL WORKING FUNCTIONS

    public Project getAccessibleProjectById(Long userId, Long projectId)
    {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new RuntimeException("there is no project with this id "+projectId));
    }

}
