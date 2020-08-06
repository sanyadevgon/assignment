package com.company.managementservice.repo;

import com.company.managementservice.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    Optional<Employee> findByfirstName(String firstName);
}
