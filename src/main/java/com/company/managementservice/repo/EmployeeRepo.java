package com.company.managementservice.repo;

import com.company.managementservice.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    Optional<Employee> findByfirstName(String firstName);

    @Transactional
    @Modifying
    @Query(value = "Delete from department_employees where employees_id=:employeeId",nativeQuery = true)
    public Integer removeEmployee(@Param("employeeId") Long employeeId);
}
