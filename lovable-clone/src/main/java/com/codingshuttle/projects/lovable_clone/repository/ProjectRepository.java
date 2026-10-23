package com.codingshuttle.projects.lovable_clone.repository;

import com.codingshuttle.projects.lovable_clone.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> // Note: use Long if your ID is Long, or keep Integer if matching your entity
{

    @Query("""
                SELECT p FROM Project p
                JOIN ProjectMember pm ON pm.project.id = p.id
                WHERE pm.user.id = :userId
                    AND p.deletedAt IS NULL
                ORDER BY p.updatedAt DESC
            """)
    List<Project> getAllProjectsAccessibleByUser(@Param("userId") Long userId);

    @Query("""
                SELECT p FROM Project p
                JOIN ProjectMember pm ON pm.project.id = p.id
                WHERE p.id = :projectId
                    AND pm.user.id = :userId
                    AND p.deletedAt IS NULL
            """)
    Optional<Project> findAccessibleProjectById(
            @Param("userId") Long userId,
            @Param("projectId") Long projectId
    );
}