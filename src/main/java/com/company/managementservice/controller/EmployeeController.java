package com.company.managementservice.controller;

import com.company.managementservice.exception.EmptyBodyException;
import com.company.managementservice.exception.MethodArgumentNotValidException;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.exception.RequestRejectedException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.dto.EmployeeDto;
import com.company.managementservice.model.dto.EmployeeInfoDto;
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
import java.util.Objects;

@Log4j2
@RestController
@Validated
@RequestMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ServiceResponse<BaseMessageResponse<EmployeeDto>> saveEmployeeDetails(@Valid @RequestBody EmployeeDto employeeDto,
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
    public ServiceResponse<BaseMessageResponse<DepartmentDto>> assignEmployeeDepartment(@NonNull @PathVariable Long employeeId,
                                                                                        @NonNull @PathVariable Long departmentId,
                                                                                        @NonNull @PathVariable Integer organisationId
    )
            throws NotFoundException, RequestRejectedException, EmptyBodyException {
        if (employeeId == 0 || Objects.isNull(employeeId))
            throw new EmptyBodyException("invalid id for employee ");
        log.info(
                "EmployeeController : assignEmployeeToDepartment : Received Request to assign Department To Employee:{} :{}"
                , departmentId, employeeId);
        return new ServiceResponse<>(
                new BaseMessageResponse(
                        employeeService.putEmployeeToDepartment(departmentId, organisationId, employeeId),
                        HttpStatus.OK, true));

    }

    @PutMapping(value = "/{employeeId}/freelance/organisation/{organisationId}")
    public ServiceResponse<BaseMessageResponse<DepartmentDto>> assignFreelancerEmployeeOrganisation(@NonNull @PathVariable Long employeeId,
                                                                   @NonNull @PathVariable Integer organisationId
    )
            throws NotFoundException, RequestRejectedException {
        log.info(
                "EmployeeController : assignEmployeeToDepartment : Received Request to assign Department To Employee:{} :{}"
                , employeeId, organisationId);
        return new ServiceResponse<>(
                new BaseMessageResponse(
                        employeeService.putFreelancerEmployeeToOrganiation(employeeId, organisationId),
                        HttpStatus.OK, true));

    }

    @DeleteMapping(value = "/{employeeId}/remove-from-department/{departmentId}")
    public ServiceResponse<BaseMessageResponse> removeEmployeeFromDepartment(@NonNull @PathVariable Long departmentId,
                                                           @NonNull @PathVariable Long employeeId
    )
            throws NotFoundException, RequestRejectedException {
        log.info(
                "EmployeeController : removeFromDepartment : Received Request to remove Employee from Department :{} :{}"
                , employeeId, departmentId);
        employeeService.removeEmployeeFromDepartment(departmentId, employeeId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Removed Successfully  ",
                        HttpStatus.OK, true));

    }

    @GetMapping(value = "/{employeeId}")
    public ServiceResponse<BaseMessageResponse<EmployeeDto>> getEmployeeDetails(@PathVariable @NonNull Long employeeId)
            throws NotFoundException, RequestRejectedException {
        log.info("EmployeeController : getEmployeeDetails  : Received Request to get Employee Details :{}", employeeId);
        return new ServiceResponse<>(
                new BaseMessageResponse(
                        employeeService.getEmployee(employeeId), HttpStatus.OK, true));

    }

    @PutMapping("/{employeeId}/update-details")
    public ServiceResponse<BaseMessageResponse<EmployeeDto>> updateEmployeeDetails(@Valid @RequestBody EmployeeDto employeeDto,
                                                    BindingResult bindingResult, @NonNull @PathVariable Long employeeId)
            throws NotFoundException, MethodArgumentNotValidException, RequestRejectedException {
        log.info("EmployeeController : putEmployeeDetails : Received Request to put Employee Details :{}", employeeId);
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

    @DeleteMapping("/{employeeId}")
    public ServiceResponse<BaseMessageResponse> removeAEmployee(@NonNull @PathVariable Long employeeId)
            throws NotFoundException, RequestRejectedException {
        log.info("EmployeeController : removeAEmployee: Received Request to remove Employee  :{}", employeeId);
        employeeService.removeEmployee(employeeId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Removed Successfully ",
                        HttpStatus.OK, true));
    }

    @GetMapping("/on-bench-list")
    public ServiceResponse<?> employeeOnBench() {
        log.info("EmployeeController : employeeOnBench: Received Request to list Employee on bench");
        return new ServiceResponse<>(
                new BaseMessageResponse<>(employeeService.employeeOnBench(), HttpStatus.OK, true));
    }

    @GetMapping("/contact-info/{employeeId}")
    public ServiceResponse<BaseMessageResponse<EmployeeInfoDto>> employeeInfo(@NonNull @PathVariable Long employeeId)
            throws NotFoundException {
        log.info("EmployeeController : employeeInfo: Received Request to retrieve Employee Info");
        return new ServiceResponse<>(
                new BaseMessageResponse<>(employeeService.getEmployeeInfo(employeeId), HttpStatus.OK, true));

    }

}
