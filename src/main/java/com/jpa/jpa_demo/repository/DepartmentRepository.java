package com.jpa.jpa_demo.repository;

import com.jpa.jpa_demo.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Basic CRUD operations are inherited from JpaRepository
}