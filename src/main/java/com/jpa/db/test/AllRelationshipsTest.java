package com.jpa.db.test;

import com.jpa.db.repository.DepartmentRepository;
import com.jpa.db.repository.EmployeeProjectRepository;
import com.jpa.db.repository.EmployeeRepository;
import com.jpa.db.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class AllRelationshipsTest implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeProjectRepository employeeProjectRepository;

    public AllRelationshipsTest(DepartmentRepository departmentRepository,
                                EmployeeRepository employeeRepository,
                                ProjectRepository projectRepository,
                                EmployeeProjectRepository employeeProjectRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.employeeProjectRepository = employeeProjectRepository;
    }

    @Override
    public void run(String... args) {

        log.info("============================================================");
        log.info("COMPREHENSIVE JPA RELATIONSHIPS TEST");
        log.info("============================================================");

        // 1. Department hierarchy
        log.info("1. DEPARTMENT HIERARCHY:");
        log.info("----------------------------------------");

        departmentRepository.findAll().forEach(dept -> {
            log.info("Department: {} ({})", dept.getName(), dept.getCode());
            log.info("Total Employees: {}", dept.getEmployees().size());

            dept.getEmployees()
                    .forEach(emp ->
                            log.info("Department Head: {} {}",
                                    emp.getFirstName(),
                                    emp.getLastName())
                    );
        });

        // 2. Project teams
        log.info("2. PROJECT TEAMS:");
        log.info("----------------------------------------");

        projectRepository.findAll().forEach(project -> {
            log.info("Project: {}", project.getName());
            log.info("Status: {}", project.getStatus());
            log.info("Budget: {}", project.getBudget());
            log.info("Team Size: {}", project.getAssignments().size());

            project.getAssignments().forEach(assignment -> {
                var emp = assignment.getEmployee();
                log.info(" - {} {}", emp.getFirstName(), emp.getLastName());
                log.info("   Dept: {}", emp.getDepartment().getName());
                log.info("   Role: {}", assignment.getRole());
                log.info("   Hours/Week: {}", assignment.getHoursPerWeek());
            });
        });

        // 3. Employee details with all relationships
        log.info("3. EMPLOYEE DETAILS WITH ALL RELATIONSHIPS:");
        log.info("----------------------------------------");

        employeeRepository.findAll().forEach(emp -> {
            log.info("{} {}", emp.getFirstName(), emp.getLastName());
            log.info("Position: {}", emp.getPosition());
            log.info("Department: {}", emp.getDepartment() != null ? emp.getDepartment().getName() : "None");

            log.info("Projects: {}", emp.getProjects().size());

            if (!emp.getProjects().isEmpty()) {
                log.info("Working on:");
                emp.getProjects().forEach(assignment ->
                        log.info(" - {} as {}",
                                assignment.getProject().getName(),
                                assignment.getRole())
                );
            }
        });

        // 4. Statistics
        log.info("4. STATISTICS:");
        log.info("----------------------------------------");
        log.info("Total Departments: {}", departmentRepository.count());
        log.info("Total Employees: {}", employeeRepository.count());
        log.info("Total Projects: {}", projectRepository.count());
        log.info("Total Project Assignments: {}", employeeProjectRepository.count());

        log.info("Employees working on multiple projects:");
        employeeRepository.findAll().stream()
                .filter(emp -> emp.getProjects().size() > 1)
                .forEach(emp ->
                        log.info(" - {} {} ({} projects)",
                                emp.getFirstName(),
                                emp.getLastName(),
                                emp.getProjects().size())
                );
    }
}

