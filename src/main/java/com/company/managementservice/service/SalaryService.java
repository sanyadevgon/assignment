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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        if(employee.get().getTerminatedDate()!=null)
            throw new NotFoundException("Employee is terminated, salary cant be updated");
        Salary salary = modelMapper.map(salaryDto, Salary.class);
        Set<Salary> salaries = employee.get().getSalaries();
        if(!salaries.isEmpty())
            throw new NotFoundException("Salary details already exists, try updating the salary details");
        salaries.add(salary);
        salaryRepo.save(salary);
        employee.get().setSalaries(salaries);
        employeeRepo.save(employee.get());
        return salaryDto;
    }

    public List<SalaryDto> getEmployeeSalary(Long id) throws NotFoundException {

        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id- " + id);
        Set<Salary> salaries = employee.get().getSalaries();
        return salaries.stream().map(e -> modelMapper.map(e, SalaryDto.class)).collect(Collectors.toList());

    }

    public SalaryDto UpdateSalary(SalaryDto salaryDto, long id) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id- " + id);
        Set<Salary> salaries = employee.get().getSalaries();
        if(salaries.isEmpty())
            throw new NotFoundException("Salary details does not exists, first set the salary details");
        if(employee.get().getTerminatedDate()!=null)
            throw new NotFoundException("Employee is terminated, salary cant be updated");
        for (Salary s: salaries) {
            if (s.getToDate() == null) {
                s.setToDate(LocalDate.now());
            }
        }
        Salary salary = modelMapper.map(salaryDto, Salary.class);
        salary.setFromDate(LocalDate.now());
        salaries.add(salary);
        employee.get().setSalaries(salaries);
        salaryRepo.save(salary);
        employeeRepo.save(employee.get());
        return salaryDto;

    }
    public SalaryDto getEmployeeCurrentSalary(Long id) throws NotFoundException {

        Optional<Employee> employee = employeeRepo.findById(id);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id- " + id);
        if(employee.get().getTerminatedDate()!=null)
            throw new NotFoundException("Employee is terminated, no current salary found");
        Salary currentSalary = new Salary();
        Set<Salary> salaries = employee.get().getSalaries();
        for (Salary s: salaries) {
            if (s.getToDate() == null) {
                currentSalary=s;
            }
        }
        if(currentSalary==null){
            throw new NotFoundException("No Salary Details found");
        }
       return modelMapper.map(currentSalary, SalaryDto.class);

    }

}
