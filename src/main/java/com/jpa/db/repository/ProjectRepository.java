package com.jpa.db.repository;

import com.jpa.db.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Find projects by status
    List<Project> findByStatus(String status);

    // Find projects within date range
    List<Project> findByStartDateBetween(LocalDate start, LocalDate end);

    // Find projects with budget greater than
    List<Project> findByBudgetGreaterThan(BigDecimal budget);
}
