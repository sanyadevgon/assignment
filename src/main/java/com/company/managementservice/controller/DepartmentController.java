package com.company.managementservice.controller;

import com.company.managementservice.exception.MethodArgumentNotValidException;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.exception.RequestRejectedException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.DepartmentService;
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

@Log4j2
@RestController
@Validated
@RequestMapping(value = "/department", consumes = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ServiceResponse<?> saveDepartmentDetails(@RequestBody DepartmentDto departmentDto,
                                                    BindingResult bindingResult)
            throws MethodArgumentNotValidException {
        log.info(
                "DepartmentController : saveDepartmentDetails : Received Request to save department Details" +
                departmentDto.toString());
        if (bindingResult.hasErrors()) {
            String errMsg = "";
            for (FieldError err: bindingResult.getFieldErrors()) {
                errMsg += err.getField() + " is " + err.getCode();
            }
            throw new MethodArgumentNotValidException(errMsg);
        }

        return new ServiceResponse<>(
                new BaseMessageResponse(departmentService.saveDepartment(departmentDto),
                                        HttpStatus.OK, true));
    }

    @GetMapping(value = "/{id}/details")
    public ServiceResponse<?> getDepartmentDetails(@NonNull @PathVariable Long id)
            throws NotFoundException, RequestRejectedException {
        if (id == null || id < 0)
            throw new RequestRejectedException("Provide valid id ");
        log.info("DepartmentController : getDepartmentDetails : Received Request to get Department Details:{}", id);
        return new ServiceResponse<>(
                new BaseMessageResponse(departmentService.getDepartment(id), HttpStatus.OK, true));

    }

    @PutMapping("/{id}/update-details")
    public ServiceResponse<?> updateDepartmentDetails(@Valid @RequestBody DepartmentDto departmentDto,
                                                      BindingResult bindingResult,
                                                      @NonNull @PathVariable Long id)
            throws NotFoundException, MethodArgumentNotValidException, RequestRejectedException {
        log.info("DepartmentController : putDepartmentDetails : Received Request to put Department Details for id:{}",
                 id);
        if (id == null || id < 0)
            throw new RequestRejectedException("Provide valid id ");
        if (bindingResult.hasErrors()) {
            String errMsg = "";
            for (FieldError err: bindingResult.getFieldErrors()) {
                errMsg += err.getField() + " is " + err.getCode();
            }
            throw new MethodArgumentNotValidException(errMsg);
        }
        return new ServiceResponse<>(
                new BaseMessageResponse(departmentService.updateDepartment(departmentDto, id),
                                        HttpStatus.OK, true));
    }

    @PostMapping("/add/company/{id}")
    public ServiceResponse<?> saveDepartmentInOrganisation(@Valid @RequestBody DepartmentDto departmentDto,
                                                           BindingResult bindingResult,
                                                           @NonNull @PathVariable Integer id)
            throws NotFoundException, MethodArgumentNotValidException, RequestRejectedException {
        log.info(
                "DepartmentController : postDepartmentInOrganisation : Received Request to post Department In organisation for id:{} ",
                id);
        if (id == null || id < 0)
            throw new RequestRejectedException("Provide valid id ");
        if (bindingResult.hasErrors()) {
            String errMsg = "";
            for (FieldError err: bindingResult.getFieldErrors()) {
                errMsg += err.getField() + " is " + err.getCode();
            }
            throw new MethodArgumentNotValidException(errMsg);
        }
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(departmentService.postDepartmentInCompany(departmentDto, id),
                                        HttpStatus.OK, true));

    }

    @PutMapping(value = "/{departmentId}/assign-organisation/{organisationId}")
    public ServiceResponse<?> assignDepartmentToOrganisation(@NonNull @PathVariable Long departmentId,
                                                             @NonNull @PathVariable Integer organisationId
    )
            throws NotFoundException, RequestRejectedException {
        log.info(
                "DepartmentController : assignDepartmentToOrganisation: Received Request to assign Department To organisation:{} :{}"
                , organisationId, departmentId);
        if (departmentId == null || organisationId == null)
            throw new RequestRejectedException("Provide valid id ");
        return new ServiceResponse<>(
                new BaseMessageResponse(
                        departmentService.putDepartmentToOrganisation(organisationId, departmentId),
                        HttpStatus.OK, true));

    }

    @DeleteMapping("/{departmentId}/remove")
    public ResponseEntity<?> removeADepartment(@NonNull @PathVariable Long departmentId)
            throws NotFoundException, RequestRejectedException {
        log.info("DepartmentController : removeDepartmentDetails : Received Request to remove Department Details :{}",
                 departmentId);
        departmentService.removeDepartment(departmentId);
        if (departmentId == null)
            throw new RequestRejectedException("Provide valid id ");
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Removed Successfully",
                        HttpStatus.OK, true));
    }

    @GetMapping(value = "/{id}/all-employees")
    public ServiceResponse<?> getEmployeeDetails(@NonNull @PathVariable Long id)
            throws NotFoundException, RequestRejectedException {
        log.info(
                "DepartmentController : getEmployeeDetails : Received Request to get Department Employees Details: id{}",
                id);
        if (id == null)
            throw new RequestRejectedException("Provide valid id ");
        return new ServiceResponse<>(
                new BaseMessageResponse<>(departmentService.getAllEmployees(id), HttpStatus.OK, true));

    }

}
