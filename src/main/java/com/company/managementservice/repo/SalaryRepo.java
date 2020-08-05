package com.company.managementservice.repo;

import com.company.managementservice.model.dto.SalaryDto;
import com.company.managementservice.model.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepo extends JpaRepository<Salary, Long> {
}
