package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.dto.SalaryDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.OrganisationDepartment;
import com.company.managementservice.model.entity.Salary;
import com.company.managementservice.repo.DepartmentRepo;
import com.company.managementservice.repo.EmployeeRepo;
import com.company.managementservice.repo.OrganisationDepartmentRepo;
import com.company.managementservice.repo.SalaryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CacheService {

    @Autowired
    OrganisationDepartmentRepo organisationDepartmentRepo;

    @Autowired
    OrganisationService organisationService;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    SalaryRepo salaryRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public void updateDepartmentCache(Long departmentId) throws NotFoundException {
        List<OrganisationDepartment> organisationDepartmentList =
                organisationDepartmentRepo.updateDepartmentInOrganCache(departmentId);
        for (OrganisationDepartment organisationDepartment: organisationDepartmentList) {
            organisationService.updateDepartmentCache(
                    organisationDepartment.getOrganisationDepartmentId().getOrganisationId(),
                    organisationDepartment.getOrganisationDepartmentId().getDepartmentId());
        }
    }

    public void removeDepartmentCache(Long departmentId) {
        List<OrganisationDepartment> organisationDepartmentList =
                organisationDepartmentRepo.updateDepartmentInOrganCache(departmentId);
        for (OrganisationDepartment organisationDepartment: organisationDepartmentList) {
            organisationService.removeDepartmentCache(
                    organisationDepartment.getOrganisationDepartmentId().getOrganisationId(),
                    organisationDepartment.getOrganisationDepartmentId().getDepartmentId());
        }
    }

    public void removeEmployeeCache(Long departmentId) {
        List<OrganisationDepartment> organisationDepartmentList =
                organisationDepartmentRepo.updateDepartmentInOrganCache(departmentId);
        for (OrganisationDepartment organisationDepartment: organisationDepartmentList) {
            organisationService.removeEmployeeCache(
                    organisationDepartment.getOrganisationDepartmentId().getOrganisationId(),
                    organisationDepartment.getOrganisationDepartmentId().getDepartmentId());
        }
    }

    public void getEmployeeDepartmentId(Long employeeId) {
        Long departmentId = employeeRepo.getDepartmentId(employeeId);
        if (departmentId != null)
            updateEmployeeCache(departmentId);
    }

    @CachePut(cacheNames = "department", key = "#departmentId")
    public DepartmentDto updateEmployeeCache(Long departmentId) {
        List<OrganisationDepartment> organisationDepartmentList =
                organisationDepartmentRepo.updateDepartmentInOrganCache(departmentId);
        for (OrganisationDepartment organisationDepartment: organisationDepartmentList) {
            organisationService.updateEmployeeCache(
                    organisationDepartment.getOrganisationDepartmentId().getOrganisationId(),
                    organisationDepartment.getOrganisationDepartmentId().getDepartmentId());
        }
        return modelMapper.map(departmentRepo.findById(departmentId), DepartmentDto.class);
    }


    @CachePut(cacheNames = "department", key = "#departmentId")
    public DepartmentDto removeEmployeeCache(Long departmentId, Long employeeId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-{}" + departmentId);
        if (!employee.isPresent())
            throw new NotFoundException("NOT FOUND employee id-{}" + employeeId);

        Set<Employee> employees = department.get().getEmployees();
        for (Employee employee1: employees) {
            if (employee1.getId() == employeeId) {
                department.get().getEmployees().remove(employee1);
                break;
            }
        }
        List<OrganisationDepartment> organisationDepartmentList =
                organisationDepartmentRepo.updateDepartmentInOrganCache(departmentId);
        for (OrganisationDepartment organisationDepartment: organisationDepartmentList) {
            organisationService.updateEmployeeCache(
                    organisationDepartment.getOrganisationDepartmentId().getOrganisationId(),
                    organisationDepartment.getOrganisationDepartmentId().getDepartmentId());
        }
        return modelMapper.map(department.get(), DepartmentDto.class);
    }

    @CachePut(cacheNames = "salary", key = "#employeeId")
    public SalaryDto updateSalaryCache(Long employeeId,Long salaryId){
        Optional<Salary> salary=salaryRepo.findById(salaryId);
        return modelMapper.map(salary.get(),SalaryDto.class);
    }

    @CacheEvict(cacheNames = "salary", key = "#employeeId")
    public void removeSalaryCacheEmp(Long employeeId){

    }

    @CacheEvict(cacheNames = "department", key = "#departmentId")
    public void removeSalaryCacheDept(Long departmentId){

    }
}
