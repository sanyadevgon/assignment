package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.EmployeeDto;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.repo.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee=employeeRepo.save(modelMapper.map(employeeDto, Employee.class));
        employeeDto.setId(employee.getId());
        return employeeDto;
    }

    public EmployeeDto getEmployee(Long id) throws NotFoundException {

        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-" + id);
        return modelMapper.map(employee.get(), EmployeeDto.class);
    }

   /* public void deleteDepartment(Long id) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(id);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + id);
        departmentRepo.deleteById(id);
    }*/

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
}

