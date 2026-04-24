package com.codingshuttle.projects.lovable_clone.service;

import com.codingshuttle.projects.lovable_clone.dto.project.FileContentResponse;
import com.codingshuttle.projects.lovable_clone.dto.project.FileNode;

import java.util.List;

public interface FileService
{
    List<FileNode> getFileTree(long projectId, Long userId);

    FileContentResponse getFileContent(String path, Long projectId, Long userId);
}
