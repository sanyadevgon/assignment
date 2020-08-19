package com.company.managementservice.service;

import com.company.managementservice.constant.Constants;
import com.company.managementservice.exception.ConstraintViolationException;
import com.company.managementservice.exception.DublicateDataException;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.OrganisationDto;
import com.company.managementservice.model.dto.OrganisationInfoDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.Organisation;
import com.company.managementservice.model.enums.DesignationType;
import com.company.managementservice.repo.DepartmentRepo;
import com.company.managementservice.repo.EmployeeRepo;
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
import java.util.*;

@Log4j2
@Service
@Transactional
public class OrganisationService {

    @Autowired
    OrganisationRepo repo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    OrganisationRepo organisationRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public OrganisationDto saveOrganisation(OrganisationDto organisationDto)
            throws NotFoundException, DublicateDataException, ConstraintViolationException {

        Organisation organisation = modelMapper.map(organisationDto, Organisation.class);
        String organisationName = organisation.getName().toLowerCase();
        if (organisationDto.getCeo() == null) {
            throw new DublicateDataException("Provide valid employee id in type Long");
        }
        Optional<Employee> employee = employeeRepo.findById(organisationDto.getCeo());
        if (employee.isPresent()) {
            if (employee.get().getDesignationType() != DesignationType.CEO) {
                throw new DublicateDataException("Employee with above id does not have designation CEO, not found ceo");
            }
            if (employee.get().getTerminatedDate() != null) {
                throw new NotFoundException("Employee(CEO) with above id left organisation, is terminated");
            }
        }

        organisation.setName(organisationName);
        organisation.setIsActive(true);
        repo.save(organisation);
        organisationDto.setId(organisation.getId());
        log.info("saveOrganisation: saving organisation to db :{}", organisationDto);
        return modelMapper.map(organisation, OrganisationDto.class);

    }

    @Cacheable(cacheNames = "organisationU", key = "#organisationId")
    public OrganisationDto getOrganisation(Integer organisationId) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(organisationId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id-" + organisationId);
        log.info("getOrganisation: get organisation from db id :{}", organisation.toString());
        return modelMapper.map(organisation.get(), OrganisationDto.class);

    }

    @CachePut(cacheNames = "organisationU", key = "#organisationId")
    public OrganisationDto updateOrganisation(Integer organisationId, OrganisationDto organisationDto)
            throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(organisationId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND id organisation-" + organisationId);
        else if (organisation.get().getIsActive() == false)
            throw new NotFoundException("Organisation is non active-" + organisationId);
        Organisation organisationInfo = modelMapper.map(organisationDto, Organisation.class);
        organisationInfo.setId(organisationId);
        organisationInfo.setCreatedAt(organisation.get().getCreatedAt());
        organisationInfo.setCreatedBy(organisation.get().getCreatedBy());
        organisationInfo.setIsActive(organisation.get().getIsActive());
        organisationInfo.setCeo(organisation.get().getCeo());
        organisationInfo.setUpdatedBy(Constants.ADMIN);
        repo.save(organisationInfo);
        return modelMapper.map(organisationInfo, OrganisationDto.class);
    }

    @CachePut(cacheNames = "organisationU", key = "#organisationId")
    public OrganisationDto removeDepartment(Integer organisationId, Long departmentId) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(organisationId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND id organisation-" + organisationId);

        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND id department-" + departmentId);
        Set<Department> depts = organisation.get().getDepartment();
        for (Department d: depts) {
            if (d.getId() == departmentId) {
                organisation.get().getDepartment().remove(d);
                break;
            }
        }
        return modelMapper.map(organisation.get(), OrganisationDto.class);

    }

    @CacheEvict(cacheNames = "organisationU", key = "#organisationId")
    public void removeOrganisation(Integer organisationId) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(organisationId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND id organisation-" + organisationId);
        organisation.get().setIsActive(false);
        organisation.get().getDepartment().clear();
        organisation.get().setUpdatedAt(LocalDateTime.now());
        organisation.get().setUpdatedBy(Constants.ADMIN);
    }

    @Transactional
    public Set<Map<String, Object>> getAllDepartments(Integer organisationId) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(organisationId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND id organisation-" + organisationId);
        Set<Map<String, Object>> departments = new HashSet<>();
        log.info("Fetching  departments of organisation from database");
        for (Department department: organisation.get().getDepartment()) {
            HashMap<String, Object> names = new HashMap<>();
            names.put("name", department.getName());
            names.put("id", department.getId());
            departments.add(names);
        }
        return departments;
    }

    @CachePut(cacheNames = "organisationU", key = "#organisationId")
    public OrganisationDto updateDepartmentCache(Integer organisationId, Long departmentId) {
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);
        return modelMapper.map(organisation.get(), OrganisationDto.class);
    }

    @CachePut(cacheNames = "organisationU", key = "#organisationId")
    public OrganisationDto removeDepartmentCache(Integer organisationId, Long departmentId) {
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);
        return modelMapper.map(organisation.get(), OrganisationDto.class);
    }

    @CachePut(cacheNames = "organisationU", key = "#organisationId")
    public OrganisationDto removeEmployeeCache(Integer organisationId, Long departmentId) {
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);
        return modelMapper.map(organisation.get(), OrganisationDto.class);
    }

    @CachePut(cacheNames = "organisationU", key = "#organisationId")
    public OrganisationDto updateEmployeeCache(Integer organisationId, Long departmentId) {
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);
        return modelMapper.map(organisation.get(), OrganisationDto.class);
    }

    public OrganisationInfoDto getOrganisationInfo(Integer organisationId) {
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);
        return modelMapper.map(organisation.get(), OrganisationInfoDto.class);
    }
}
