package com.company.managementservice.controller;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.EmployeeDto;
import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.EmployeeService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController()
@RequestMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ServiceResponse<?> saveEmployeeDetails(@Valid @RequestBody EmployeeDto employeeDto) {
        log.info(
                "EmployeeController : postEmployeeDetails : Received Request to post Employee Details" +
                employeeDto.toString());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Saved Successfully  " + employeeService.saveEmployee(employeeDto),
                                        HttpStatus.OK, true));

    }

    @PutMapping(value = "add/{employeeId}/department/{departmentId}")
    public ServiceResponse<?> assignEmployeeToDepartment(@NonNull @PathVariable Long departmentId, @NonNull @PathVariable Long employeeId
    )
            throws NotFoundException {
        log.info(
                "EmployeeController : assignEmployeeToDepartment : Received Request to assign Department To Employee:{} :{}"
                , departmentId, employeeId);
        employeeService.putEmployeeToDepartment(departmentId,employeeId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Saved Successfully  ",
                        HttpStatus.OK, true));

    }
    @PutMapping(value = "remove/{employeeId}/department/{departmentId}")
    public ServiceResponse<?> removeEmployeeFromDepartment(@NonNull @PathVariable Long departmentId, @NonNull @PathVariable Long employeeId
    )
            throws NotFoundException {
        log.info(
                "EmployeeController : removeFromDepartment : Received Request to remove Employee from Department :{} :{}"
                , employeeId, departmentId);
        employeeService.removeEmployeeFromDepartment(departmentId,employeeId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Removed Successfully  ",
                        HttpStatus.OK, true));

    }
    @GetMapping(value = "/get/{id}")
    public ServiceResponse<?> getEmployeeDetails(@NonNull @PathVariable Long id) throws NotFoundException {
        log.info("EmployeeController : getEmployeeDetails  : Received Request to get Employee Details :{}", id);
        return new ServiceResponse<>(
                employeeService.getEmployee(id), HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ServiceResponse<?> updateEmployeeDetails(@Valid @RequestBody EmployeeDto employeeDto,@NonNull @PathVariable Long id)
            throws NotFoundException {
        log.info("EmployeeController : putEmployeeDetails : Received Request to put Employee Details :{}", id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Updated Successfully " + employeeService.updateEmployee(employeeDto, id),
                        HttpStatus.OK, true));
    }

    @PutMapping("/terminate/{id}")
    public ServiceResponse<?> removeAEmployee(@NonNull @PathVariable Long id)
            throws NotFoundException {
        log.info("EmployeeController : removeAEmployee: Received Request to remove Employee  :{}", id);
        employeeService.removeEmployee(id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Removed Successfully " ,
                        HttpStatus.OK, true));
    }


}
