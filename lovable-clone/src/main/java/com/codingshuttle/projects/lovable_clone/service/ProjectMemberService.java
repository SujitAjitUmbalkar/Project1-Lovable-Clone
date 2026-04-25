package com.codingshuttle.projects.lovable_clone.service;

import com.codingshuttle.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.codingshuttle.projects.lovable_clone.dto.member.MemberResponse;
import com.codingshuttle.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;

import java.util.List;

public interface ProjectMemberService
{
    List<MemberResponse> getProjectMembers(long projectId, Long userId);

    MemberResponse inviteMember(long projectId, InviteMemberRequest request, Long userId);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId);
}
