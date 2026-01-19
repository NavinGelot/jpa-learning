package com.jpa.jpa_demo.test;

import com.jpa.jpa_demo.domain.Department;
import com.jpa.jpa_demo.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class NPlusOneFixed implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;

    public NPlusOneFixed(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        log.info("============================================================");
        log.info("FIXING N+1 WITH JOIN FETCH");
        log.info("============================================================");

        log.info("Using JOIN FETCH query:");
        log.info("Expected: ONLY 1 query executed");

        // Executes ONE query with LEFT JOIN FETCH
        List<Department> departments = departmentRepository.findAllWithEmployees();
//        List<Department> departments = departmentRepository.findAll(); // @EntityGraph solution

        log.info("Found {} departments", departments.size());

        // No extra queries triggered here
        departments.forEach(dept -> {
            log.info("Department: {}", dept.getName());
            log.info("Has {} employees", dept.getEmployees().size());
        });
    }
}
