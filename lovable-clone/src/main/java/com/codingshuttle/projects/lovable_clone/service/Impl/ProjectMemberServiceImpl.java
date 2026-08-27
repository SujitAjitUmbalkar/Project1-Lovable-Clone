package com.codingshuttle.projects.lovable_clone.service.Impl;

import com.codingshuttle.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.codingshuttle.projects.lovable_clone.dto.member.MemberResponse;
import com.codingshuttle.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.codingshuttle.projects.lovable_clone.entity.Project;
import com.codingshuttle.projects.lovable_clone.entity.ProjectMember;
import com.codingshuttle.projects.lovable_clone.entity.User;
import com.codingshuttle.projects.lovable_clone.mapper.ProjectMemberMapper;
import com.codingshuttle.projects.lovable_clone.repository.ProjectMemberRepository;
import com.codingshuttle.projects.lovable_clone.repository.ProjectRepository;
import com.codingshuttle.projects.lovable_clone.service.ProjectMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService
{
    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    ProjectMemberMapper projectMemberMapper;

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
    public MemberResponse inviteMember(long projectId, InviteMemberRequest request, Long userId) {
        return null;
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        return null;
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
