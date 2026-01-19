package com.jpa.jpa_demo.test;

import com.jpa.jpa_demo.domain.Department;
import com.jpa.jpa_demo.domain.Employee;
import com.jpa.jpa_demo.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class NPlusOneDemo implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;

    public NPlusOneDemo(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        log.info("============================================================");
        log.info("DEMONSTRATING N+1 PROBLEM");
        log.info("============================================================");

        log.info("STEP 1: Fetch all departments");
        log.info("Expected: 1 query for departments");

        List<Department> departments = departmentRepository.findAll();
        log.info("Found {} departments", departments.size());

        log.info("STEP 2: Access employees for each department");
        log.info("Expected: +N queries for employees (N+1 total)");
        log.info("------------------------------------------------------------");

        int totalEmployees = 0;

        for (Department dept : departments) {
            log.info("Department: {} (id={})", dept.getName(), dept.getId());

            // THIS LINE TRIGGERS N+1:
            // Each access causes a separate SELECT on employees
            List<Employee> employees = dept.getEmployees();

            log.info("Has {} employees", employees.size());

            employees.forEach(emp ->
                    log.info(" - {} {}", emp.getFirstName(), emp.getLastName())
            );

            totalEmployees += employees.size();
        }

        log.info("------------------------------------------------------------");
        log.info("TOTAL QUERY COUNT:");
        log.info("1 (departments) + {} (employees) = {} queries executed", departments.size(), 1 + departments.size());
        log.info("Total employees retrieved: {}", totalEmployees);
    }
}
