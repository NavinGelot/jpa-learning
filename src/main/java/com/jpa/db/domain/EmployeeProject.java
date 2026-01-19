package com.jpa.db.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_projects")
@Data
public class EmployeeProject {

    @EmbeddedId
    private EmployeeProjectId id = new EmployeeProjectId();

    @Column(name = "role")
    private String role;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "hours_per_week")
    private Integer hoursPerWeek;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

}
