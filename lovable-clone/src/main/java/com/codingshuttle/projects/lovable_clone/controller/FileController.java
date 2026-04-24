package com.codingshuttle.projects.lovable_clone.controller;

import com.codingshuttle.projects.lovable_clone.dto.project.FileContentResponse;
import com.codingshuttle.projects.lovable_clone.dto.project.FileNode;
import com.codingshuttle.projects.lovable_clone.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/files")
public class FileController
{
    public  final FileService fileService;

    @GetMapping
    public ResponseEntity<List<FileNode>> getFileTree(@PathVariable long projectId)
    {
        Long userId = 1L;
        return ResponseEntity.ok(fileService.getFileTree(userId, projectId));
    }

    @GetMapping("/{*path}")     // all contents after / ( /api/projects/10/files/ ->  src/main/App.java
    public ResponseEntity<FileContentResponse> getFile(@PathVariable Long projectId , @PathVariable String path)
    {
        Long userId = 1L;
        return ResponseEntity.ok(fileService.getFileContent(path, projectId, userId));
    }
}
