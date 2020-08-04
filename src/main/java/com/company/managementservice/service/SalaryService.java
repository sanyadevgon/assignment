package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.SalaryDto;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.Salary;
import com.company.managementservice.repo.EmployeeRepo;
import com.company.managementservice.repo.SalaryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SalaryService {

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public SalaryDto saveSalary(Long id, SalaryDto salaryDto) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-" + id);
        Salary salary = salaryRepo.save(modelMapper.map(salaryDto, Salary.class));
        salaryDto.setId(salary.getId());
        return salaryDto;
    }

}
