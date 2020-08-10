package com.company.managementservice.controller;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.SalaryDto;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.SalaryService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@Validated
@RequestMapping(value = "/salary", consumes = MediaType.APPLICATION_JSON_VALUE)
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping(value = "/{id}")//first salary post
    public ServiceResponse<?> saveSalaryDetails(@NonNull @PathVariable Long id, @Valid @RequestBody SalaryDto salaryDto)
            throws NotFoundException {
        log.info(
                "SalaryController : postSalaryDetails : Received Request to post Salary Details " +
                salaryDto.toString());
        return new ServiceResponse<>(
                salaryService.saveSalary(id, salaryDto),
                HttpStatus.OK);

    }

    @GetMapping(value = "/{employeeId}/details")
    public ServiceResponse<?> getEmployeeSalary(@NonNull @PathVariable Long employeeId) throws NotFoundException {
        log.info("SalaryController : getEmployeeSalary  : Received Request to get Employee Salary  :{}", employeeId);
        return new ServiceResponse<>(
                salaryService.getEmployeeSalary(employeeId), HttpStatus.OK);

    }

    @PutMapping("/{employeeId}/update")
    public ServiceResponse<?> updateEmployeeSalary(@Valid @RequestBody SalaryDto salaryDto,
                                                   @NonNull @PathVariable Long employeeId)
            throws NotFoundException {
        log.info("SalaryController : updateSalaryDetails : Received Request to update Salary Details   :{}",
                 employeeId);
        return new ServiceResponse<>(
                salaryService.UpdateSalary(salaryDto, employeeId).toString(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{employeeId}/current-details")
    public ServiceResponse<?> getEmployeeCurrentSalary(@NonNull @PathVariable Long employeeId)
            throws NotFoundException {
        log.info("SalaryController : getEmployeeCurrentSalary  : Received Request to get Employee Current Salary  :{}",
                 employeeId);
        return new ServiceResponse<>(
                salaryService.getEmployeeCurrentSalary(employeeId), HttpStatus.OK);

    }

}

