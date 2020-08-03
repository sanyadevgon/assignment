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

@RestController
@Log4j2
@RequestMapping(value = "/organisation")
public class OrganisationController {

    @Autowired
    OrganisationService organisationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postOrganisationDetails(
            @RequestBody OrganisationDto organisationDto) throws EmptyBodyException {
        if (organisationDto.getName() == null || organisationDto.getHeadOfficeLocation() == null)
            throw new EmptyBodyException("Name and location cannot be empty fields");

        log.info(
                "OrganisationController : save Organisation Details : Received Request to save Organisation" +
                organisationDto);
        OrganisationDto organisationDto1=organisationService.saveOrganisation(organisationDto);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Saved Successfully "+organisationDto1.toString(), HttpStatus.OK, true));

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> retrieveOrganisation(@PathVariable Integer id) throws NotFoundException {
        log.info("OrganisationController : get Organisation Details : Received Request to get Organisation", id);
        return new ServiceResponse<>(
                organisationService.getOrganisation(id), HttpStatus.OK);

    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> deletedOrganisation(@PathVariable Integer id) throws NotFoundExcep
    ganisation Details : Received Request to delete Organisation", id);
        organisationService.deleteOrganisation(id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Deleted Successfully", HttpStatus.OK, true));
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrganisation(@RequestBody OrganisationDto organisationDto,
                                                @PathVariable Integer id) throws NotFoundException {

        log.info("OrganisationController : Put Organisation Details : Received Request to put Organisation", id);
        return new ServiceResponse<>(
                organisationService.updateOrganisation(organisationDto, id), HttpStatus.OK);

    }

}
