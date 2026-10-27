package com.codingshuttle.projects.lovable_clone.service.Impl;

import com.codingshuttle.projects.lovable_clone.dto.project.ProjectRequest;
import com.codingshuttle.projects.lovable_clone.dto.project.ProjectResponse;
import com.codingshuttle.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.codingshuttle.projects.lovable_clone.entity.Project;
import com.codingshuttle.projects.lovable_clone.entity.ProjectMember;
import com.codingshuttle.projects.lovable_clone.entity.ProjectMemberId;
import com.codingshuttle.projects.lovable_clone.entity.User;
import com.codingshuttle.projects.lovable_clone.enums.ProjectRole;
import com.codingshuttle.projects.lovable_clone.error.ResourceNotFoundException;
import com.codingshuttle.projects.lovable_clone.mapper.ProjectMapper;
import com.codingshuttle.projects.lovable_clone.repository.ProjectMemberRepository;
import com.codingshuttle.projects.lovable_clone.repository.ProjectRepository;
import com.codingshuttle.projects.lovable_clone.repository.UserRepository;
import com.codingshuttle.projects.lovable_clone.security.AuthUtils;
import com.codingshuttle.projects.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
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
    ProjectMemberRepository projectMemberRepository;
    AuthUtils authUtils;

    @Override
    public ProjectResponse createProject(ProjectRequest request)
    {
        Long userId = authUtils.getCurrentUserId();

//        User owner = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));

        User owner = userRepository.getReferenceById(userId);

        Project project = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();

         project = projectRepository.save(project);

        ProjectMemberId projectMemberId = new ProjectMemberId( project.getId(), userId);

        ProjectMember projectMember = ProjectMember.builder()
                .project(project)
                .projectRole(ProjectRole.OWNER)
                .id(projectMemberId)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .build();

                projectMemberRepository.save(projectMember);

         return projectMapper.toProjectResponse(project);

    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects()
    {
        Long userId = authUtils.getCurrentUserId();

        return
                projectMapper.toListOfProjectSummaryResponse(projectRepository.getAllProjectsAccessibleByUser(userId));
    }

    @Override
    @PreAuthorize("@security.canViewProject(#projectId)")
    public ProjectResponse getUserProjectById(Long projectId)
    {
        Long userId = authUtils.getCurrentUserId();

        Project project = getAccessibleProjectById(userId, projectId);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request)
    {
        Long userId = authUtils.getCurrentUserId();
        Project project = getAccessibleProjectById(userId, id);

        project.setName(request.name());

        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long id)
    {
        Long userId = authUtils.getCurrentUserId();

        Project project = getAccessibleProjectById(userId, id);

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    ///  INTERNAL WORKING FUNCTIONS

    public Project getAccessibleProjectById(Long userId, Long projectId)
    {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new ResourceNotFoundException("project", projectId.toString()));
    }
}
