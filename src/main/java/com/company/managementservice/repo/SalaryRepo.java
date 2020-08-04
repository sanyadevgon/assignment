package com.company.managementservice.repo;

import com.company.managementservice.model.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepo extends JpaRepository<Salary, Long> {
}
