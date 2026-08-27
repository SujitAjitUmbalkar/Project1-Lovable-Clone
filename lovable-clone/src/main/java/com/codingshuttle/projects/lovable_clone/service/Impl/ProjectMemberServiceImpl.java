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
import java.util.ArrayList;
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
//        1. get project
        Project project = getAccessibleProjectById(projectId , userId);

//        2. though we have to return list of memberResponse ,  create it and add members
        List<MemberResponse> memberResponseList = new ArrayList<>();

//        2. get owner
        User user = project.getOwner();

    //        4. to add user owner in it , convert user to memberResponse
        MemberResponse memberResponse = projectMemberMapper.toMemberResponseFromOwner(user);

//        5. add user in it
        memberResponseList.add(memberResponse);

//        6. get list of other members from ProjectMember Repo
        List<ProjectMember> projectMembers = projectMemberRepository.findByIdProjectId(projectId);

//      7. convert projectMember into MemberResponse
       List<MemberResponse> memberResponseList1 =  projectMembers.stream()
                .map(projectMember -> projectMemberMapper.toMemberResponseFromMember(projectMember))
                .collect(Collectors.toList());

//       8. Add it
        memberResponseList.addAll(memberResponseList1);

//       9. return it
        return memberResponseList;

    }

    @Override
    public MemberResponse inviteMember(long projectId, InviteMemberRequest request, Long userId)
    {
//        1. get the project
        Project project = getAccessibleProjectById(userId , projectId);

//        2. check permission
        if(!project.getOwner().getId().equals(userId))
        {
            throw new RuntimeException("Not Allowed, You are not an owner ");
        }

//        3. fetch the invitee from the user Repository
        User invitee = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("There is no User with email " + request.email()));

//        4. check if the inviter is not inviting himself
        if(invitee.getId().equals(userId))
        {
            throw new RuntimeException("Cannot invite yourself");
        }

//        5. check if the invitee is not invited before
        ProjectMemberId projectMemberId = new  ProjectMemberId(projectId, invitee.getId());
        if(projectMemberRepository.existsById(projectMemberId))
        {
            throw new RuntimeException("Cannot invite again");
        }

//        create project member by provided info
        ProjectMember member = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();

//        save member
        projectMemberRepository.save(member);

//        return after converting ProjectMember -> MemberResponse
        return projectMemberMapper.toMemberResponseFromMember(member);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId)
    {
        //        1. get the project
        Project project = getAccessibleProjectById(userId , projectId);

//        2. check permission
        if(!project.getOwner().getId().equals(userId))
        {
            throw new RuntimeException("Not Allowed, You are not an owner ");
        }

//        3. create projectMemberId and find the Member of Project via projectMemberId
        ProjectMemberId projectMemberId = new  ProjectMemberId(projectId, memberId);
        ProjectMember member = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

//        4. set role as requested and save
        member.setProjectRole(request.role());
        projectMemberRepository.save(member);

//        5. convert ProjectMember to MemberResponse and return
        return projectMemberMapper.toMemberResponseFromMember(member);
    }

    @Override
    public MemberResponse deleteMember(Long projectId, Long memberId, Long userId) {
        return null;
    }

    ///  INTERNAL WORKING FUNCTIONS

    public Project getAccessibleProjectById(Long userId, Long projectId)
    {
        return projectRepository.findAccessibleProjectById(userId, projectId)
                .orElseThrow(() -> new RuntimeException("there is no project with this id "+projectId));
    }

}
