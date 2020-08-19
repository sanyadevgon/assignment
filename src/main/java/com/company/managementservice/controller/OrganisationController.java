package com.company.managementservice.controller;

import com.company.managementservice.exception.ConstraintViolationException;
import com.company.managementservice.exception.DublicateDataException;
import com.company.managementservice.exception.MethodArgumentNotValidException;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.OrganisationDto;
import com.company.managementservice.model.dto.OrganisationInfoDto;
import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.OrganisationService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@Log4j2
@Validated
@RequestMapping(value = "/organisation", consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    @PostMapping
    public ResponseEntity<OrganisationDto> saveOrganisationDetails(
            @Valid @RequestBody OrganisationDto organisationDto, BindingResult bindingResult)
            throws MethodArgumentNotValidException, NotFoundException, ConstraintViolationException,
                   DublicateDataException {
        log.info(
                "OrganisationController : saveOrganisationDetails : Received Request to save Organisation Details " +
                organisationDto);

        if (bindingResult.hasErrors()) {
            String errMsg = "";
            for (FieldError err: bindingResult.getFieldErrors()) {
                errMsg += err.getField() + " is " + err.getCode();
            }
            throw new MethodArgumentNotValidException(errMsg);
        }
        return new ServiceResponse<>(
                new BaseMessageResponse<>(
                        organisationService.saveOrganisation(organisationDto),
                        HttpStatus.OK, true));

    }

    @GetMapping(value = "/{organisationId}/details")
    public ResponseEntity<OrganisationDto> getOrganisation(@PathVariable @NonNull Integer organisationId)
            throws NotFoundException {
        log.info("OrganisationController : getOrganisationDetails : Received Request to get Organisation Details :{}",
                 organisationId);
        return new ServiceResponse<>(
                new BaseMessageResponse<>(
                        organisationService.getOrganisation(organisationId), HttpStatus.OK, true));

    }

    @PutMapping("/{organisationId}/update-details")
    public ResponseEntity<OrganisationDto> updateOrganisation(@PathVariable Integer organisationId,
                                                              @Valid @RequestBody OrganisationDto organisationDto,
                                                              BindingResult bindingResult
    )
            throws NotFoundException, MethodArgumentNotValidException {
        log.info("OrganisationController : PutOrganisationDetails : Received Request to put Organisation Details  :{}",
                 organisationId);
        if (bindingResult.hasErrors()) {
            String errMsg = "";
            for (FieldError err: bindingResult.getFieldErrors()) {
                errMsg += err.getField() + " is " + err.getCode();
            }
            throw new MethodArgumentNotValidException(errMsg);
        }
        return new ServiceResponse<>(
                new BaseMessageResponse<>(organisationService.updateOrganisation(organisationId, organisationDto),
                                          HttpStatus.OK, true));
    }

    @DeleteMapping("/{organisationId}/remove-department/{departmentId}")
    public ResponseEntity<BaseMessageResponse> removeDepartment(@NonNull @PathVariable Integer organisationId,
                                                                @NonNull @PathVariable Long departmentId)
            throws NotFoundException {
        log.info(
                "OrganisationController : removeDepartment : Received request to remove Department from organisation id :{} ",
                organisationId);
        organisationService.removeDepartment(organisationId, departmentId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Updated Successfully ",
                        HttpStatus.OK, true));
    }

    @DeleteMapping("/{id}/remove-organisation")
    public ResponseEntity<BaseMessageResponse> removeOrganisation(@NonNull @PathVariable Integer id)
            throws NotFoundException {
        log.info(
                "OrganisationController : removeOrganisationDetails : Received Request to remove Organisation Details id :{}",
                id);
        organisationService.removeOrganisation(id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Removed Successfully ",
                        HttpStatus.OK, true));
    }

    @GetMapping(value = "/{id}/all-departments")
    public ServiceResponse<BaseMessageResponse<Set<Map<String, Object>>>> getDepartmentDetails(
            @NonNull @PathVariable Integer id) throws NotFoundException {
        log.info("OrganisationController : getDepartmentDetails : Received Request to get Department  Details id:{}",
                 id);
        return new ServiceResponse<>(
                new BaseMessageResponse(organisationService.getAllDepartments(id), HttpStatus.OK, true));

    }

    @GetMapping(value = "/{id}")
    public ServiceResponse<BaseMessageResponse<OrganisationInfoDto>> getOrganisationInfo(
            @NonNull @PathVariable Integer id) throws NotFoundException {
        log.info("OrganisationController : getDepartmentDetails : Received Request to get Department  Details id:{}",
                 id);
        return new ServiceResponse<>(
                new BaseMessageResponse(organisationService.getOrganisationInfo(id), HttpStatus.OK, true));

    }

}
