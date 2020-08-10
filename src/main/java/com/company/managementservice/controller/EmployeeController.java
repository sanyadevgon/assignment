package com.company.managementservice.controller;

import com.company.managementservice.exception.MethodArgumentNotValidException;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.exception.RequestRejectedException;
import com.company.managementservice.model.dto.EmployeeDto;
import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.EmployeeService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController()
@Validated
@RequestMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ServiceResponse<?> saveEmployeeDetails(@Valid @RequestBody EmployeeDto employeeDto,
                                                  BindingResult bindingResult)
            throws MethodArgumentNotValidException {
        log.info(
                "EmployeeController : postEmployeeDetails : Received Request to post Employee Details" +
                employeeDto.toString());
        if (bindingResult.hasErrors()) {
            String errMsg = "";
            for (FieldError err: bindingResult.getFieldErrors()) {
                errMsg += err.getField() + " is " + err.getCode();
            }
            throw new MethodArgumentNotValidException(errMsg);
        }
        return new ServiceResponse<>(
                new BaseMessageResponse(employeeService.saveEmployee(employeeDto),
                                        HttpStatus.OK, true));

    }

    @PutMapping(value = "/{employeeId}/assign-department/{departmentId}/in-organisation/{organisationId}")
    public ServiceResponse<?> assignEmployeeDepartment(@NonNull @PathVariable Long employeeId,
                                                       @NonNull @PathVariable Long departmentId,
                                                       @NonNull @PathVariable Integer organisationId
    )
            throws NotFoundException, RequestRejectedException {
        log.info(
                "EmployeeController : assignEmployeeToDepartment : Received Request to assign Department To Employee:{} :{}"
                , departmentId, employeeId);
        if (employeeId == null || departmentId == null || organisationId == null)
            throw new RequestRejectedException("Provide valid id ");

        return new ServiceResponse<>(
                new BaseMessageResponse(
                        employeeService.putEmployeeToDepartment(employeeId, departmentId, organisationId),
                        HttpStatus.OK, true));

    }

    @PutMapping(value = "/{employeeId}/freelance/organisation/{organisationId}")
    public ServiceResponse<?> assignFreelancerEmployeeOrganisation(@NonNull @PathVariable Long employeeId,
                                                                   @NonNull @PathVariable Integer organisationId
    )
            throws NotFoundException, RequestRejectedException {
        log.info(
                "EmployeeController : assignEmployeeToDepartment : Received Request to assign Department To Employee:{} :{}"
                , employeeId, organisationId);
        if (employeeId == null || organisationId == null)
            throw new RequestRejectedException("Provide valid id ");

        return new ServiceResponse<>(
                new BaseMessageResponse(
                        employeeService.putFreelancerEmployeeToOrganiation(employeeId, organisationId),
                        HttpStatus.OK, true));

    }

    @DeleteMapping(value = "/{employeeId}/remove-from-department/{departmentId}")
    public ServiceResponse<?> removeEmployeeFromDepartment(@NonNull @PathVariable Long departmentId,
                                                           @NonNull @PathVariable Long employeeId
    )
            throws NotFoundException, RequestRejectedException {
        log.info(
                "EmployeeController : removeFromDepartment : Received Request to remove Employee from Department :{} :{}"
                , employeeId, departmentId);
        if (employeeId == null || departmentId == null)
            throw new RequestRejectedException("Provide valid id ");
        employeeService.removeEmployeeFromDepartment(departmentId, employeeId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Removed Successfully  ",
                        HttpStatus.OK, true));

    }

    @GetMapping(value = "/{employeeId}/details")
    public ServiceResponse<?> getEmployeeDetails(@NonNull @PathVariable Long employeeId)
            throws NotFoundException, RequestRejectedException {
        log.info("EmployeeController : getEmployeeDetails  : Received Request to get Employee Details :{}", employeeId);
        if (employeeId == null)
            throw new RequestRejectedException("Provide valid id ");
        return new ServiceResponse<>(
                new BaseMessageResponse(
                        employeeService.getEmployee(employeeId), HttpStatus.OK, true));

    }

    @PutMapping("/{employeeId}/update-details")
    public ServiceResponse<?> updateEmployeeDetails(@Valid @RequestBody EmployeeDto employeeDto,
                                                    BindingResult bindingResult, @NonNull @PathVariable Long employeeId)
            throws NotFoundException, MethodArgumentNotValidException, RequestRejectedException {
        log.info("EmployeeController : putEmployeeDetails : Received Request to put Employee Details :{}", employeeId);
        if (employeeId == null)
            throw new RequestRejectedException("Provide valid id ");
        if (bindingResult.hasErrors()) {
            String errMsg = "";
            for (FieldError err: bindingResult.getFieldErrors()) {
                errMsg += err.getField() + " is " + err.getCode();
            }
            throw new MethodArgumentNotValidException(errMsg);
        }
        return new ServiceResponse<>(
                new BaseMessageResponse<>(
                        employeeService.updateEmployee(employeeDto, employeeId),
                        HttpStatus.OK, true));
    }

    @DeleteMapping("/{employeeId}/terminate")
    public ServiceResponse<?> removeAEmployee(@NonNull @PathVariable Long employeeId)
            throws NotFoundException, RequestRejectedException {
        log.info("EmployeeController : removeAEmployee: Received Request to remove Employee  :{}", employeeId);
        if (employeeId == null)
            throw new RequestRejectedException("Provide valid id ");
        employeeService.removeEmployee(employeeId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Removed Successfully ",
                        HttpStatus.OK, true));
    }

}
