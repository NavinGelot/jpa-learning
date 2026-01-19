package com.jpa.db.repository;

import com.jpa.db.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employees by department
    List<Employee> findByDepartmentId(Long departmentId);

    // Find employees by manager
    List<Employee> findByManagerId(Long managerId);

    // Find employees working on a specific project
    @Query("SELECT e FROM Employee e JOIN e.projects p WHERE p.project.id = :projectId")
    List<Employee> findEmployeesByProjectId(@Param("projectId") Long projectId);

    // Find employees with salary greater than
    List<Employee> findBySalaryGreaterThan(BigDecimal salary);
}
