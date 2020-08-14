package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.OrganisationDepartment;
import com.company.managementservice.repo.DepartmentRepo;
import com.company.managementservice.repo.EmployeeRepo;
import com.company.managementservice.repo.OrganisationDepartmentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        Long departmentId = employeeRepo.getDepartmentId(employeeId);//if dept id is null?
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

    public void getEmployeeDepartmentIdForRemoval(Long employeeId) throws NotFoundException {
        Long departmentId = employeeRepo.getDepartmentId(employeeId);
        if (departmentId != null)
            removeEmployeeCache(departmentId, employeeId);
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
}
