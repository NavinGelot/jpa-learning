package com.jpa.jpa_demo.test;

import com.jpa.jpa_demo.domain.Employee;
import com.jpa.jpa_demo.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ManyToOneTest implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public ManyToOneTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {

        log.info("=== TESTING @ManyToOne RELATIONSHIP ===");

        // Fetch employee with ID 1 (John Doe)
        employeeRepository.findById(1L).ifPresentOrElse(john -> {
            log.info("Employee: {} {}", john.getFirstName(), john.getLastName());

            if (john.getDepartment() != null) {
                log.info("Department: {}", john.getDepartment().getName());
                log.info("Department Code: {}", john.getDepartment().getCode());
            } else {
                log.warn("Employee has no department assigned");
            }

        }, () -> log.warn("Employee not found with id=1"));

        // Fetch all employees in Engineering department
        log.info("=== All Employees in Engineering ===");

        employeeRepository.findAll().stream()
                .filter(emp -> emp.getDepartment() != null)
                .filter(emp -> "Engineering".equals(emp.getDepartment().getName()))
                .forEach(emp ->
                        log.info("{} {} - {}",
                                emp.getFirstName(),
                                emp.getLastName(),
                                emp.getPosition())
                );
    }
}
