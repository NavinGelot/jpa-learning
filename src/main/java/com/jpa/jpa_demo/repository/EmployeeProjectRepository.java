package com.jpa.jpa_demo.repository;

import com.jpa.jpa_demo.domain.EmployeeProject;
import com.jpa.jpa_demo.domain.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {
}
