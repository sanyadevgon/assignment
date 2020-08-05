package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Organisation;
import com.company.managementservice.repo.DepartmentRepo;
import com.company.managementservice.repo.OrganisationRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private OrganisationRepo organisationRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        Department department = departmentRepo.save(modelMapper.map(departmentDto, Department.class));
        departmentDto.setId(department.getId());
        return modelMapper.map(department, DepartmentDto.class);
    }

    public DepartmentDto getDepartment(Long id) throws NotFoundException {

        Optional<Department> department = departmentRepo.findById(id);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + id);
        DepartmentDto departmentDto=modelMapper.map(department.get(), DepartmentDto.class);
        departmentDto.setEmployees(department.get().getEmployees());
        return departmentDto;
    }

    public DepartmentDto updateDepartment(DepartmentDto departmentDto, Long id) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(id);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + id);
        Department departmentInfo = modelMapper.map(departmentDto, Department.class);
        departmentInfo.setId(id);
        departmentInfo.setCreatedAt(department.get().getCreatedAt());
        departmentInfo.setCreatedBy(department.get().getCreatedBy());
        departmentRepo.save(departmentInfo);
        return modelMapper.map(departmentInfo, DepartmentDto.class);

    }

    public DepartmentDto postDepartmentInCompany(DepartmentDto departmentDto, Integer id) throws NotFoundException {
        Optional<Organisation> organisation = organisationRepo.findById(id);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id-" + id);
        Department department = departmentRepo.save(modelMapper.map(departmentDto, Department.class));
        departmentDto.setId(department.getId());
        Set<Department> departments = organisation.get().getDepartment();
        departments.add(department);
        organisation.get().setDepartment(departments);
        organisationRepo.save(organisation.get());
        return modelMapper.map(departmentDto, DepartmentDto.class);

    }
    public void putDepartmentToOrganisation(Integer companyId, Long departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        Optional<Organisation> organisation = organisationRepo.findById(companyId);

        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + departmentId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id-" + companyId);

        Set<Department> departments = organisation.get().getDepartment();
        departments.add(department.get());
        organisation.get().setDepartment(departments);
        organisationRepo.save(organisation.get());

    }

    public void removeDepartment(Long departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND id department-" + departmentId);
        department.get().setIsActive(false);
        department.get().setUpdatedAt(LocalDateTime.now());
        department.get().setUpdatedBy("admin");

    }
}
