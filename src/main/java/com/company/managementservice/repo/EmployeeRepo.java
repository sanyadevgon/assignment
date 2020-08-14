package com.company.managementservice.repo;

import com.company.managementservice.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    Optional<Employee> findByfirstName(String firstName);

    @Transactional
    @Modifying
    @Query(value = "Delete from department_employees where employees_id=:employeeId", nativeQuery = true)
    public Integer removeEmployee(@Param("employeeId") Long employeeId);

    @Query(value = "Select department_id from department_employees where employees_id=:employeeId", nativeQuery = true)
    public Long getDepartmentId(@Param("employeeId") Long employeeId);

    @Query(value = "Select employees_id from department_employees where department_id=:departmentId",nativeQuery = true)
    public List<Long> getEmployeeId(@Param("departmentId") Long departmentId);

    @Modifying
    @Transactional
    @Query(value = "update salary set amount = amount+:inc where \n" +
                   "to_date is null and employee_id in (\n" +
                   "select employees_id from department_employees department_id= :departmentId )",nativeQuery = true)
    public void updateSalaryByDepartment(@Param("inc") Long inc, @Param("departmentId") Long departmentId);
}
