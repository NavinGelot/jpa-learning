package com.jpa.jpa_demo.repository;

import com.jpa.jpa_demo.domain.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Basic CRUD operations are inherited from JpaRepository

    // BAD: Causes N+1
//    List<Department> findAll();

    // GOOD: Single query with JOIN FETCH
    @Query("SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.employees")
    List<Department> findAllWithEmployees();

    // Also GOOD: Using EntityGraph
    @EntityGraph(attributePaths = {"employees"})
    List<Department> findAll();

}