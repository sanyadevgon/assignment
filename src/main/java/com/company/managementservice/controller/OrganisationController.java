package com.company.managementservice.controller;

import com.company.managementservice.exception.EmptyBodyException;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.OrganisationDto;
import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.OrganisationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Log4j2
@RequestMapping(value = "/organisation", consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    @PostMapping
    public ResponseEntity<?> saveOrganisationDetails(
            @Valid @RequestBody OrganisationDto organisationDto) throws EmptyBodyException {
        if (organisationDto.getName() == null || organisationDto.getHeadOfficeLocation() == null)
            throw new EmptyBodyException("Name and location cannot be empty fields");

        log.info(
                "OrganisationController : saveOrganisationDetails : Received Request to save Organisation Details" +
                organisationDto);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Saved Successfully " + organisationService.saveOrganisation(organisationDto).toString(),
                        HttpStatus.OK, true));

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOrganisation(@PathVariable Integer id) throws NotFoundException {
        log.info("OrganisationController : getOrganisationDetails : Received Request to get Organisation Details", id);
        return new ServiceResponse<>(
                organisationService.getOrganisation(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putOrganisation(@Valid @RequestBody OrganisationDto organisationDto,
                                             @PathVariable Integer id) throws NotFoundException {
        log.info("OrganisationController : PutOrganisationDetails : Received Request to put Organisation Details", id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Updated Successfully " +
                        organisationService.updateOrganisation(organisationDto, id).toString(),
                        HttpStatus.OK, true));
    }

}
