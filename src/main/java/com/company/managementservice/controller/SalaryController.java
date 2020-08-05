package com.company.managementservice.controller;



import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.SalaryDto;
import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.SalaryService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping(value = "/salary", consumes = MediaType.APPLICATION_JSON_VALUE)
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping(value="/{id}")
    public ServiceResponse<?> postSalaryDetails(@Valid @PathVariable Long id,@Valid @RequestBody SalaryDto salaryDto)
            throws NotFoundException {
        log.info(
                "SalaryController : postSalaryDetails : Received Request to post Salary Details" +
                salaryDto.toString());
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Saved Successfully  " + salaryService.saveSalary(id,salaryDto).toString(),
                                        HttpStatus.OK, true));

    }

    @GetMapping(value = "/salaries/{id}")
    public ServiceResponse<?> getEmployeeSalary(@NonNull @PathVariable Long id) throws NotFoundException {
        log.info("SalaryController : getEmployeeSalary  : Received Request to get Employee Salary  :{}", id);
        return new ServiceResponse<>(
                salaryService.getEmployeeSalary(id), HttpStatus.OK);

    }

    /*@GetMapping(value = "/{id}")
    public ServiceResponse<?> getSalaryDetails(@PathVariable Long id) throws NotFoundException {
        log.info("SalaryDController : getSalaryDetails  : Received Request to get SalaryD Details", id);
        return new ServiceResponse<>(
                salaryService.getSalary(id), HttpStatus.OK);

    }*/


    @PutMapping("/{id}")
    public ServiceResponse<?> putEmployeeDetails(@RequestBody SalaryDto salaryDto, @PathVariable Long id)
            throws NotFoundException {
        log.info("SalaryController : putSalaryDetails : Received Request to put Salary Details", id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Updated Successfully " + salaryService.UpdateSalary(salaryDto, id).toString(),
                        HttpStatus.OK, true));
    }

}

