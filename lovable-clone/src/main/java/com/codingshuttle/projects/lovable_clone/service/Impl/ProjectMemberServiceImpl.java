package com.codingshuttle.projects.lovable_clone.service.Impl;

import com.codingshuttle.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.codingshuttle.projects.lovable_clone.dto.member.MemberResponse;
import com.codingshuttle.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.codingshuttle.projects.lovable_clone.entity.Project;
import com.codingshuttle.projects.lovable_clone.entity.ProjectMember;
import com.codingshuttle.projects.lovable_clone.entity.ProjectMemberId;
import com.codingshuttle.projects.lovable_clone.entity.User;
import com.codingshuttle.projects.lovable_clone.mapper.ProjectMemberMapper;
import com.codingshuttle.projects.lovable_clone.repository.ProjectMemberRepository;
import com.codingshuttle.projects.lovable_clone.repository.ProjectRepository;
import com.codingshuttle.projects.lovable_clone.repository.UserRepository;
import com.codingshuttle.projects.lovable_clone.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService
{
    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    ProjectMemberMapper projectMemberMapper;
    UserRepository userRepository;

    @Override
    public List<MemberResponse> getProjectMembers(long projectId, Long userId)
    {
        Project project = getAccessibleProjectById(userId , projectId );     // not needed in method but for security purpose

        return projectMemberRepository.findByIdProjectId(projectId)
                .stream()
                .map(projectMember -> projectMemberMapper.toMemberResponseFromMember(projectMember))
                .collect(Collectors.toList());

    }

    @Override
    public MemberResponse inviteMember(long projectId, InviteMemberRequest request, Long userId)
    {
        Project project = getAccessibleProjectById(userId , projectId);

        User invitee = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("There is no User with userName " + request.username()));

        if(invitee.getId().equals(userId))
        {
            throw new RuntimeException("Cannot invite yourself");
        }

        ProjectMemberId projectMemberId = new  ProjectMemberId(projectId, invitee.getId());
        if(projectMemberRepository.existsById(projectMemberId))
        {
            throw new RuntimeException("Cannot invite again");
        }

        ProjectMember member = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();

        projectMemberRepository.save(member);

        return projectMemberMapper.toMemberResponseFromMember(member);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId)
    {
        Project project = getAccessibleProjectById(userId , projectId);

        ProjectMemberId projectMemberId = new  ProjectMemberId(projectId, memberId);
        ProjectMember member = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        member.setProjectRole(request.role());
        projectMemberRepository.save(member);

        return projectMemberMapper.toMemberResponseFromMember(member);
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId, Long userId)
    {
        Project project = getAccessibleProjectById(userId , projectId);

        ProjectMemberId projectMemberId = new  ProjectMemberId(projectId, memberId);
        if(!projectMemberRepository.existsById(projectMemberId))
        {
            throw new RuntimeException("There is no such member with id " + memberId);
        }

        projectMemberRepository.deleteById(projectMemberId);

    }

    ///  INTERNAL WORKING FUNCTIONS

    public Project getAccessibleProjectById(Long userId, Long projectId)
    {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new RuntimeException("there is no project with this id "+projectId));
    }

}
