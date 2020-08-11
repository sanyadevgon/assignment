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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private OrganisationRepo organisationRepo;

    @Autowired
    private OrganisationDepartmentRepo organisationDepartmentRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        Department department = modelMapper.map(departmentDto, Department.class);
        String departmentName = department.getName().toLowerCase();
        department.setName(departmentName);
        department.setIsActive(true);
        departmentRepo.save(department);
        departmentDto.setId(department.getId());
        return modelMapper.map(department, DepartmentDto.class);
    }

    public DepartmentDto getDepartment(Long id) throws NotFoundException {

        Optional<Department> department = departmentRepo.findById(id);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-{} " + id);
        DepartmentDto departmentDto = modelMapper.map(department.get(), DepartmentDto.class);
        departmentDto.setEmployees(department.get().getEmployees());
        return departmentDto;
    }

    public DepartmentDto updateDepartment(DepartmentDto departmentDto, Long id) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(id);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + id);
        Department departmentInfo = modelMapper.map(departmentDto, Department.class);
        departmentInfo.setIsActive(department.get().getIsActive());
        departmentInfo.setId(id);
        departmentInfo.setCreatedAt(department.get().getCreatedAt());
        departmentInfo.setCreatedBy(department.get().getCreatedBy());
        departmentInfo.setIsActive(department.get().getIsActive());
        departmentInfo.setEmployees(department.get().getEmployees());
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

    }//not considering direct posting department to organisation

    public OrganisationDto putDepartmentToOrganisation(Integer companyId, Long departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        Optional<Organisation> organisation = organisationRepo.findById(companyId);

        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id- " + departmentId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id- " + companyId);
        if (department.get().getIsActive()==false)
            throw new NotFoundException("Department is not active with trying change its status to active first- " + departmentId);
        if (organisation.get().getIsActive()==false)
            throw new NotFoundException("organisation is not active with trying change its status to active first- " + companyId);
        Set<Department> departments = organisation.get().getDepartment();
        departments.add(department.get());
        organisation.get().setDepartment(departments);
        OrganisationDto organisationDto=modelMapper.map(organisation.get(),OrganisationDto.class);
        organisationRepo.save(organisation.get());
        return organisationDto;

    }

    public void removeDepartment(Long departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND id department id- " + departmentId);
        department.get().setIsActive(false);
        department.get().setUpdatedAt(LocalDateTime.now());
        department.get().setUpdatedBy(Constants.ADMIN);
        department.get().getEmployees().clear();
        departmentRepo.save(department.get());
        organisationDepartmentRepo.removeDepartment(departmentId);
    }

    public Set<Employee> getAllEmployees(Long departmentId) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(departmentId);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND id department id- " + departmentId);
        return  department.get().getEmployees();
    }
}
