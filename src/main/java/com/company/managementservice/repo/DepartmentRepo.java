package com.company.managementservice.repo;

import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
    Optional<Department> findById(Long id);
}
