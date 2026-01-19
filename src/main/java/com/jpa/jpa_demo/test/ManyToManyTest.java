package com.jpa.jpa_demo.test;

import com.jpa.jpa_demo.repository.EmployeeProjectRepository;
import com.jpa.jpa_demo.repository.EmployeeRepository;
import com.jpa.jpa_demo.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class ManyToManyTest implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeProjectRepository employeeProjectRepository;

    public ManyToManyTest(EmployeeRepository employeeRepository,
                          ProjectRepository projectRepository,
                          EmployeeProjectRepository employeeProjectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.employeeProjectRepository = employeeProjectRepository;
    }

    @Override
    public void run(String... args) {

        log.info("=== TESTING MANY-TO-MANY RELATIONSHIP ===");

        // Test 1: Find all projects for an employee
        employeeRepository.findById(1L).ifPresentOrElse(john -> {
            log.info("Employee: {} {}", john.getFirstName(), john.getLastName());
            log.info("Projects assigned:");

            john.getProjects().forEach(assignment -> {
                log.info(" - Project: {}", assignment.getProject().getName());
                log.info("   Role: {}", assignment.getRole());
                log.info("   Hours/Week: {}", assignment.getHoursPerWeek());
            });

        }, () -> log.warn("Employee not found with id=1"));

        // Test 2: Find all employees on a project
        log.info("=== Test 2: Find all employees on a project ===");

        projectRepository.findById(1L).ifPresentOrElse(ecommerce -> {
            log.info("Project: {}", ecommerce.getName());
            log.info("Team Members:");

            ecommerce.getAssignments().forEach(assignment -> {
                var emp = assignment.getEmployee();
                log.info(" - {} {}", emp.getFirstName(), emp.getLastName());
                log.info("   Role: {}", assignment.getRole());
                log.info("   Hours: {}", assignment.getHoursPerWeek());
            });

        }, () -> log.warn("Project not found with id=1"));

        // Test 3: Add new project assignment
        log.info("=== Test 3: Add new project assignment ===");

        var janeOpt = employeeRepository.findById(2L);
        var mobileAppOpt = projectRepository.findById(2L);

        if (janeOpt.isPresent() && mobileAppOpt.isPresent()) {
            var jane = janeOpt.get();
            var mobileApp = mobileAppOpt.get();

            boolean alreadyAssigned = jane.getProjects().stream()
                    .anyMatch(a -> a.getProject().getId().equals(mobileApp.getId()));

            if (alreadyAssigned) {
                log.info("Jane is already assigned to project {}", mobileApp.getName());
            } else {
                jane.assignToProject(mobileApp, "Lead Developer", 40);
                employeeRepository.save(jane);
                log.info("Assigned Jane to project {}", mobileApp.getName());
            }

        } else {
            log.warn("Jane or Mobile App project not found (employeeId=2, projectId=2)");
        }
    }
}
