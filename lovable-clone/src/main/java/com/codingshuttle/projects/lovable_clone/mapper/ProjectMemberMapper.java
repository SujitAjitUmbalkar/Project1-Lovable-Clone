package com.codingshuttle.projects.lovable_clone.mapper;

import com.codingshuttle.projects.lovable_clone.dto.member.MemberResponse;
import com.codingshuttle.projects.lovable_clone.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper
{

    @Mapping(target = "userId" , source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "name" , source = "user.name")
    MemberResponse toMemberResponseFromMember(ProjectMember member);

}
