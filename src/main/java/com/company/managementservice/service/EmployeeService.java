package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.EmployeeDto;
import com.company.managementservice.model.dto.SalaryDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.Salary;
import com.company.managementservice.repo.DepartmentRepo;
import com.company.managementservice.repo.EmployeeRepo;
import com.company.managementservice.repo.SalaryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class
EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private SalaryRepo salaryRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = employeeRepo.save(modelMapper.map(employeeDto, Employee.class));
        employeeDto.setId(employee.getId());
        return employeeDto;
    }

    public void putEmployeeToDepartment(Long departmentId, Long employeeId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        Optional<Employee> employee = employeeRepo.findById(employeeId);

        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + departmentId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-" + employeeId);

        Set<Employee> employees = department.get().getEmployees();
        employees.add(employee.get());
        department.get().setEmployees(employees);
        departmentRepo.save(department.get());

    }
    public void removeEmployeeFromDepartment(Long departmentId, Long employeeId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        Optional<Employee> employee = employeeRepo.findById(employeeId);

        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + departmentId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-" + employeeId);

        Set<Employee> employees = department.get().getEmployees();
        for(Employee e : employees){
            if(e.getId()==employeeId)
                department.get().getEmployees().remove(e);

        }


    }

    public EmployeeDto getEmployee(Long id) throws NotFoundException {

        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-" + id);
        return modelMapper.map(employee.get(), EmployeeDto.class);
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto, long id) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-" + id);
        Employee employeeInfo = modelMapper.map(employeeDto, Employee.class);
        employeeInfo.setId(id);
        employeeInfo.setCreatedAt(employee.get().getCreatedAt());
        employeeInfo.setCreatedBy(employee.get().getCreatedBy());
        employeeInfo.setHireDate(employee.get().getHireDate());
        employeeRepo.save(employeeInfo);
        return modelMapper.map(employeeInfo, EmployeeDto.class);

    }

    public void removeEmployee(Long employeeId) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-" + employeeId);
        employee.get().setIsActive(false);
        employee.get().setUpdatedAt(LocalDateTime.now());
        employee.get().setUpdatedBy("admin");
        employee.get().setTerminatedDate(LocalDate.now());
        Set<Salary> salaries=employee.get().getSalaries();
        for(Salary s :salaries){
            if(s.getIsCurrent()==true) {
                s.setIsCurrent(false);
                s.setToDate(LocalDate.now());
            }
        }
    }


}

