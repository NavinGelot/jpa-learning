package com.jpa.jpa_demo.test;

import com.jpa.jpa_demo.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class OneToManyTest implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;

    public OneToManyTest(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional // required as eager
    public void run(String... args) {

        log.info("=== TESTING @OneToMany RELATIONSHIP ===");

        // Fetch Engineering department (ID: 1)
        departmentRepository.findById(1L).ifPresentOrElse(engineering -> {

            log.info("Department: {}", engineering.getName());
            log.info("Number of Employees: {}", engineering.getEmployees().size());

            log.info("Employees in Engineering:");
            engineering.getEmployees().forEach(emp ->
                    log.info(" - {} {} ({})",
                            emp.getFirstName(),
                            emp.getLastName(),
                            emp.getPosition())
            );

        }, () -> log.warn("Department not found with id=1"));
    }
}
