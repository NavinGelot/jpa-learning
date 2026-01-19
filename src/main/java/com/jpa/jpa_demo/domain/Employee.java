package com.jpa.jpa_demo.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "position")
    private String position;

//    @Column(name = "department_id")
//    private Long departmentId;  // Just a foreign key column, not a relationship yet

    @Column(name = "manager_id")
    private Long managerId;     // Just a foreign key column, not a relationship yet

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // MANY-TO-ONE: Many employees can belong to ONE department
    @ManyToOne
    @JoinColumn(name = "department_id")  // Foreign key column
    private Department department;  // it's an object, not just ID!

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeProject> projects = new ArrayList<>();

    public void assignToProject(Project project, String role, Integer hoursPerWeek) {
        EmployeeProject assignment = new EmployeeProject();
        assignment.setEmployee(this);
        assignment.setProject(project);
        assignment.setRole(role);
        assignment.setHoursPerWeek(hoursPerWeek);
        assignment.setAssignedDate(LocalDate.now());
        this.projects.add(assignment);
        project.getAssignments().add(assignment);
    }
}
