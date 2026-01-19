package com.jpa.jpa_demo.repository;

import com.jpa.jpa_demo.domain.EmployeeProject;
import com.jpa.jpa_demo.domain.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {


    // Find all assignments for an employee
    List<EmployeeProject> findByEmployeeId(Long employeeId);

    // Find all assignments for a project
    List<EmployeeProject> findByProjectId(Long projectId);

    // Find specific assignment
    Optional<EmployeeProject> findByEmployeeIdAndProjectId(Long employeeId, Long projectId);

}
