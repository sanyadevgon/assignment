package com.company.managementservice.service;

import com.company.managementservice.constant.Constants;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.dto.EmployeeDto;
import com.company.managementservice.model.dto.OrganisationDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.Organisation;
import com.company.managementservice.model.entity.Salary;
import com.company.managementservice.model.enums.DesignationType;
import com.company.managementservice.repo.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@Transactional
public class
EmployeeService {

    @Autowired
    AuthService authService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private OrganisationDepartmentRepo organisationDepartmentRepo;

    @Autowired
    private OrganisationRepo organisationRepo;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private  CacheService cacheService;

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        String employeeName = employee.getFirstName().toLowerCase();
        employee.setFirstName(employeeName);
        employee.setIsActive(true);
        employeeRepo.save(employee);
        employeeDto.setId(employee.getId());
        return employeeDto;
    }

    @Caching(put = {@CachePut(cacheNames = "department", key = "#departmentId")})
    public DepartmentDto putEmployeeToDepartment(Long departmentId, Integer organisationId, Long employeeId)
            throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);

        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id- {}" + departmentId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id- {}" + employeeId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id- {}" + organisationId);
        if (employee.get().getTerminatedDate() != null)
            throw new NotFoundException("Employee has been terminated from service");
        Integer checkDepartmentExitsInOrganisation =
                organisationDepartmentRepo.findOrganisationByDepartment(departmentId, organisationId);
        if (checkDepartmentExitsInOrganisation == 0) {
            throw new NotFoundException("No department found in this organisation");
        }
        Set<Employee> employees = department.get().getEmployees();
        employee.get().setUpdatedAt(LocalDateTime.now());
        employee.get().setUpdatedBy(Constants.ADMIN);
        employees.add(employee.get());
        department.get().setEmployees(employees);
        DepartmentDto departmentDto = modelMapper.map(department.get(), DepartmentDto.class);
        departmentRepo.save(department.get());
        authService.addEmployeeToDepartment(organisationId);
        return departmentDto;
    }

    @CachePut(cacheNames = "department", key = "#departmentId")////////also for organisationId
    //TODO
    public OrganisationDto putFreelancerEmployeeToOrganiation(Long employeeId, Integer organisationId)
            throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);

        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id- {}" + employeeId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id- {}" + organisationId);
        if (employee.get().getTerminatedDate() != null)
            throw new NotFoundException("Employee has been terminated from service");
        if (employee.get().getDesignationType() != DesignationType.FREELANCER)
            throw new NotFoundException("Employee Designation type not a freelancer");
        Optional<Department> departmentfree = departmentRepo.findById(10L);
        Set<Employee> employees = departmentfree.get().getEmployees();
        employee.get().setUpdatedAt(LocalDateTime.now());
        employee.get().setUpdatedBy(Constants.ADMIN);
        employees.add(employee.get());
        departmentfree.get().setEmployees(employees);
        departmentRepo.save(departmentfree.get());
        Set<Department> departments = organisation.get().getDepartment();
        departments.add(departmentfree.get());
        organisation.get().setDepartment(departments);
        OrganisationDto organisationDto = modelMapper.map(organisation.get(), OrganisationDto.class);
        organisationRepo.save(organisation.get());
        authService.addEmployeeToDepartment(organisationId);
        return organisationDto;

    }

    @CachePut(cacheNames = "department", key = "#departmentId")
    public DepartmentDto removeEmployeeFromDepartment(Long departmentId, Long employeeId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-{}" + departmentId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-{}" + employeeId);

        Set<Employee> employees = department.get().getEmployees();
        for (Employee employee1: employees) {
            if (employee1.getId() == employeeId)
                department.get().getEmployees().remove(employee1);
        }//doubt-solved departmentRepo.save(department.get());, not req hibernate delete query works here
        cacheService.removeEmployeeCache(departmentId);
        return modelMapper.map(department.get(), DepartmentDto.class);
    }

    @Cacheable(cacheNames = "employee", key = "#employeeId")
    public EmployeeDto getEmployee(Long employeeId) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-{} " + employeeId);
        log.info("employeeService :getEmployee: getEmployee from db :{}", employeeId);
        EmployeeDto employeeDto = modelMapper.map(employee.get(), EmployeeDto.class);
        employeeDto.setHireDate(employee.get().getHireDate());
        employeeDto.setTerminatedDate(employee.get().getTerminatedDate());
        // employeeDto.setSalaries(employee.get().getSalaries());//?both sides in salary as well as employee
        return employeeDto;

    }

    @CachePut(cacheNames = "employee", key = "#employeeId")
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long employeeId) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-{} " + employeeId);
        if (employee.get().getTerminatedDate() != null)
            throw new NotFoundException("Employee terminated id-{} " + employeeId);
        log.info("employeeService :updateEmployee: put Employee in db :{}", employeeId);
        Employee employeeInfo = modelMapper.map(employeeDto, Employee.class);
        employeeInfo.setIsActive(employee.get().getIsActive());
        employeeInfo.setId(employeeId);
        employeeInfo.setCreatedAt(employee.get().getCreatedAt());
        employeeInfo.setCreatedBy(employee.get().getCreatedBy());
        employeeInfo.setHireDate(employee.get().getHireDate());
        employeeInfo.setSalaries(employee.get().getSalaries());
        employeeRepo.save(employeeInfo);
        cacheService.getEmployeeDepartmentId(employeeId);
        return modelMapper.map(employeeInfo, EmployeeDto.class);

    }

    @CacheEvict(cacheNames = "employee", key = "#employeeId")
    public void removeEmployee(Long employeeId) throws NotFoundException {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-{} " + employeeId);
        log.info("employeeService :removeEmployee: remove Employee from db :{}", employeeId);
        employee.get().setIsActive(false);
        employee.get().setUpdatedAt(LocalDateTime.now());
        employee.get().setUpdatedBy(Constants.ADMIN);
        employee.get().setTerminatedDate(LocalDate.now());
        Set<Salary> salaries = employee.get().getSalaries();
        for (Salary salary: salaries) {
            if (salary.getToDate() == null) {
                salary.setToDate(LocalDate.now());
                salaryRepo.save(salary);
            }
        }
        employeeRepo.save(employee.get());
        cacheService.getEmployeeDepartmentIdForRemoval(employeeId);
        employeeRepo.removeEmployee(employeeId);
        authService.removeAuthcache(employee.get());
    }

}

