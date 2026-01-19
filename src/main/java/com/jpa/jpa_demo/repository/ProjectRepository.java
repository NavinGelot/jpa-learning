package com.jpa.jpa_demo.repository;

import com.jpa.jpa_demo.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Basic CRUD operations are inherited from JpaRepository
}
