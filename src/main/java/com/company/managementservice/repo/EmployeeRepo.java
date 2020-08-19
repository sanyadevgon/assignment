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

    @Query(value = "Select employees_id from department_employees where department_id=:departmentId",
           nativeQuery = true)
    public List<Long> getEmployeeId(@Param("departmentId") Long departmentId);

    @Modifying
    @Transactional
    @Query(value = "update salary set amount = amount+:inc where \n" +
                   "to_date is null and employee_id in (\n" +
                   "select employees_id from department_employees where department_id= :departmentId )",
           nativeQuery = true)
    public void updateSalaryByDepartment(@Param("inc") Long inc, @Param("departmentId") Long departmentId);

    @Modifying
    @Transactional
    @Query(value = "update salary set amount = amount+:percent*amount*0.01 where \n" +
                   "to_date is null and employee_id in (\n" +
                   "select employees_id from department_employees where department_id= :departmentId )",
           nativeQuery = true)
    public void updateSalaryByDepartmentPercentage(@Param("percent") Long percent,
                                                   @Param("departmentId") Long departmentId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE salary SET amount = amount+:inc WHERE \n" +
                   "                                     to_date IS NULL AND employee_id IN (SELECT department_employees.employees_id FROM\n" +
                   "                   department_employees INNER JOIN organisation_department ON\n" +
                   "                   department_employees.department_id=organisation_department.department_id WHERE organisation_id=:organisationId)",
           nativeQuery = true)
    public void updateSalaryByOrganisation(@Param("inc") Long inc,
                                           @Param("organisationId") Integer organisationId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE salary SET amount = amount+:percent*amount*0.01 WHERE \n" +
                   "                                     to_date IS NULL AND employee_id IN (SELECT department_employees.employees_id FROM\n" +
                   "                   department_employees INNER JOIN organisation_department ON\n" +
                   "                   department_employees.department_id=organisation_department.department_id WHERE organisation_id=:organisationId)",
           nativeQuery = true)
    public void updateSalaryByOrganisationPercentage(@Param("percent") Long inc,
                                                     @Param("organisationId") Integer organisationId);

    @Query(value = "SELECT * FROM employee WHERE terminated_date IS NULL AND id NOT IN (SELECT employees_id FROM department_employees)",
           nativeQuery = true)
    public List<Employee> employeeesOnBench();


    @Query(value = "SELECT department_employees.employees_id FROM\n" +
                   " department_employees INNER JOIN organisation_department ON department_employees.department_id=organisation_department.department_id WHERE organisation_id=:organisationId",nativeQuery = true)
    public List<Long> getEmployeeIdOrgan(@Param("organisationId") Integer organisationId);
}
