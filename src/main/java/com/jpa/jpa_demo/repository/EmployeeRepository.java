package com.jpa.jpa_demo.repository;

import com.jpa.jpa_demo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Basic CRUD operations are inherited from JpaRepository
}
