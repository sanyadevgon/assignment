package com.company.managementservice.repo;

import com.company.managementservice.model.entity.DepartmentEmployees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DepartmentEmployeesRepo /*extends JpaRepository<DepartmentEmployees, Long> */{

  /*  @Transactional
    @Modifying
    @Query(value = "Delete from DepartmentEmployees where employees_id=:employeeId")
    public Integer removeEmployee(@Param("employeeId") Long employeeId);*/

}
