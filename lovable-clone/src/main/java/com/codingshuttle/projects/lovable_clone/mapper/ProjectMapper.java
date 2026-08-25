package com.codingshuttle.projects.lovable_clone.mapper;

import com.codingshuttle.projects.lovable_clone.dto.project.ProjectResponse;
import com.codingshuttle.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.codingshuttle.projects.lovable_clone.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper
{

    ProjectResponse toProjectResponse(Project project);

//    @Mapping(source = "name" , target = "projectName")
    ProjectSummaryResponse toProjectSummaryResponse(Project project);
//    Either of them
    List<ProjectSummaryResponse> toListOfProjectSummaryResponse(List<Project> projects);
}
