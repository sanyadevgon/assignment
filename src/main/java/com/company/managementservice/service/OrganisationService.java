package com.company.managementservice.service;



import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.dto.OrganisationDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.model.entity.Organisation;
import com.company.managementservice.repo.OrganisationRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrganisationService {

    @Autowired
    OrganisationRepo repo;

    private ModelMapper modelMapper = new ModelMapper();

    public OrganisationDto saveOrganisation(OrganisationDto organisationDto) {

        Organisation organisation=repo.save(modelMapper.map(organisationDto, Organisation.class));
        organisationDto.setId(organisation.getId());
        return modelMapper.map(organisation,OrganisationDto.class);

    }

    public OrganisationDto getOrganisation(Integer id) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(id);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id-" + id);

        return modelMapper.map(organisation.get(),OrganisationDto.class);
    }


    /*public void deleteOrganisation(Integer id) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(id);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND organisation id-" + id);
        repo.deleteById(id);
    }*/


    public OrganisationDto updateOrganisation(OrganisationDto organisationDto, Integer id) throws NotFoundException {
        Optional<Organisation> organisation = repo.findById(id);
        if (!organisation.isPresent())
            throw new NotFoundException("NOT FOUND id organisation-" + id);
        Organisation organisationInfo = modelMapper.map(organisationDto, Organisation.class);
        organisationInfo.setId(id);
        organisationInfo.setCreatedAt(organisation.get().getCreatedAt());
        organisationInfo.setCreatedBy(organisation.get().getCreatedBy());
        repo.save(organisationInfo);
        return modelMapper.map(organisationInfo,OrganisationDto.class);

    }

}
