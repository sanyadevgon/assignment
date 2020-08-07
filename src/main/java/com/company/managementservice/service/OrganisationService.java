package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.OrganisationDto;
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
public class OrganisationService {

    @Autowired
    OrganisationRepo repo;

    @Autowired
    DepartmentRepo departmentRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public OrganisationDto saveOrganisation(OrganisationDto organisationDto) {

        Organisation organisation = modelMapper.map(organisationDto, Organisation.class);
        String organisationName = organisation.getName().toLowerCase();
        organisation.setName(organisationName);
        repo.save(organisation);
        organisationDto.setId(organisation.getId());
        return modelMapper.map(organisation, OrganisationDto.class);

    }

    public OrganisationDto getOrganisation(Integer id) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(id);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id-" + id);

        return modelMapper.map(organisation.get(), OrganisationDto.class);
    }

    public OrganisationDto updateOrganisation(OrganisationDto organisationDto, Integer id) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(id);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND id organisation-" + id);
        Organisation organisationInfo = modelMapper.map(organisationDto, Organisation.class);
        organisationInfo.setId(id);
        organisationInfo.setCreatedAt(organisation.get().getCreatedAt());
        organisationInfo.setCreatedBy(organisation.get().getCreatedBy());
        repo.save(organisationInfo);
        return modelMapper.map(organisationInfo, OrganisationDto.class);

    }

    public void removeDepartment(Integer organisationId, Long departmentId) throws NotFoundException {
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

    }

    public void removeOrganisation(Integer organisationId) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(organisationId);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND id organisation-" + organisationId);
        organisation.get().setIsActive(false);
        organisation.get().getDepartment().clear();
        organisation.get().setUpdatedAt(LocalDateTime.now());
        organisation.get().setUpdatedBy("admin");

    }

}
