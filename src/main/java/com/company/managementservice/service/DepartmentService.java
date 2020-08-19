package com.company.managementservice.service;

import com.company.managementservice.constant.Constants;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.dto.OrganisationDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.Organisation;
import com.company.managementservice.repo.DepartmentRepo;
import com.company.managementservice.repo.OrganisationDepartmentRepo;
import com.company.managementservice.repo.OrganisationRepo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private OrganisationRepo organisationRepo;

    @Autowired
    private OrganisationDepartmentRepo organisationDepartmentRepo;

    @Autowired
    private CacheService cacheService;

    private ModelMapper modelMapper = new ModelMapper();

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        Department department = modelMapper.map(departmentDto, Department.class);
        String departmentName = department.getName().toLowerCase();
        department.setName(departmentName);
        department.setIsActive(true);
        department.setCreatedBy(Constants.ADMIN);
        departmentRepo.save(department);
        departmentDto.setId(department.getId());
        return modelMapper.map(department, DepartmentDto.class);
    }

    @Cacheable(cacheNames = "department", key = "#departmentId")
    public DepartmentDto getDepartment(Long departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-{} " + departmentId);
        log.info("getDepartment: get Department from db :{}",departmentId);
        DepartmentDto departmentDto = modelMapper.map(department.get(), DepartmentDto.class);
        return departmentDto;
    }

    @CachePut(cacheNames = "department", key = "#departmentId")
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + departmentId);
        log.info("updateDepartment: update Department in db :{}",departmentId);
        Department departmentInfo = modelMapper.map(departmentDto, Department.class);
        departmentInfo.setIsActive(department.get().getIsActive());
        departmentInfo.setId(departmentId);
        departmentInfo.setCreatedAt(department.get().getCreatedAt());
        departmentInfo.setCreatedBy(department.get().getCreatedBy());
        departmentInfo.setIsActive(department.get().getIsActive());
        departmentInfo.setEmployees(department.get().getEmployees());
        departmentRepo.save(departmentInfo);
        cacheService.updateDepartmentCache(departmentId);
        return modelMapper.map(departmentInfo, DepartmentDto.class);

    }

    @CachePut(cacheNames = "organisationU",key = "#organisationId")
    public OrganisationDto putDepartmentToOrganisation(Integer organisationId, Long departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);

        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id- " + departmentId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id- " + organisationId);
        if (department.get().getIsActive()==false)
            throw new NotFoundException("Department is not active" + departmentId);
        if (organisation.get().getIsActive()==false)
            throw new NotFoundException("organisation is not active with trying change its status to active first- " + organisationId);
        Set<Department> departments = organisation.get().getDepartment();
        departments.add(department.get());
        organisation.get().setDepartment(departments);
        OrganisationDto organisationDto=modelMapper.map(organisation.get(),OrganisationDto.class);
        organisationRepo.save(organisation.get());
        return organisationDto;

    }

    @CacheEvict(cacheNames = "department", key = "#departmentId")
    public void removeDepartment(Long departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND id department id- " + departmentId);
        department.get().setIsActive(false);
        department.get().setUpdatedAt(LocalDateTime.now());
        department.get().setUpdatedBy(Constants.ADMIN);
        department.get().getEmployees().clear();
        departmentRepo.save(department.get());
        cacheService.removeDepartmentCache(departmentId);
        organisationDepartmentRepo.removeDepartment(departmentId);
    }

    public Set<Employee> getAllEmployees(Long departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND id department id- " + departmentId);
        return  department.get().getEmployees();
    }
}
