package com.company.managementservice.controller;

import com.company.managementservice.constant.Constants;
import com.company.managementservice.exception.ConstraintViolationException;
import com.company.managementservice.exception.EmptyBodyException;
import com.company.managementservice.exception.MethodArgumentNotValidException;
import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.KafkaDto;
import com.company.managementservice.model.dto.SalaryDto;
import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.SalaryService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@Validated
@RequestMapping(value = "/salary", consumes = MediaType.APPLICATION_JSON_VALUE)
public class SalaryController {

    @Autowired
    private SalaryService salaryService;


    @PostMapping(value = "/{id}")
    public ServiceResponse<BaseMessageResponse<SalaryDto>> saveSalaryDetails(@NonNull @PathVariable Long id,
                                                                             @Valid @RequestBody SalaryDto salaryDto)
            throws NotFoundException {
        log.info(
                "SalaryController : postSalaryDetails : Received Request to post Salary Details " +
                salaryDto.toString());
        return new ServiceResponse<>(
                new BaseMessageResponse(salaryService.saveSalary(id, salaryDto),
                                        HttpStatus.OK, true));

    }

    @GetMapping(value = "/{employeeId}/details")
    public ServiceResponse<List<SalaryDto>> getEmployeeSalary(@NonNull @PathVariable Long employeeId)
            throws NotFoundException {
        log.info("SalaryController : getEmployeeSalary  : Received Request to get Employee Salary  :{}", employeeId);
        return new ServiceResponse<>(
                salaryService.getEmployeeSalary(employeeId), HttpStatus.OK);

    }

    @PutMapping("/{employeeId}/update")
    public ServiceResponse<BaseMessageResponse<SalaryDto>> updateEmployeeSalary(@Valid @RequestBody SalaryDto salaryDto,
                                                                                @NonNull @PathVariable Long employeeId)
            throws NotFoundException, ConstraintViolationException {
        log.info("SalaryController : updateSalaryDetails : Received Request to update Salary Details   :{}",
                 employeeId);
        return new ServiceResponse<>(
                new BaseMessageResponse(salaryService.UpdateSalary(salaryDto, employeeId),
                                        HttpStatus.OK, true));
    }

    @GetMapping(value = "/{employeeId}/current-details")
    public ServiceResponse<SalaryDto> getEmployeeCurrentSalary(@NonNull @PathVariable Long employeeId)
            throws NotFoundException {
        log.info("SalaryController : getEmployeeCurrentSalary  : Received Request to get Employee Current Salary  :{}",
                 employeeId);
        return new ServiceResponse<>(
                salaryService.getEmployeeCurrentSalary(employeeId), HttpStatus.OK);

    }

    @PutMapping(value = "/absolute/{increment}/{currency}/update-by-department/{departmentId}")
    public ResponseEntity<BaseMessageResponse> updateSalaryByDepartmentAbsolute(@PathVariable Long increment, @PathVariable String currency,
                                                                                @NonNull @PathVariable Long departmentId)
            throws MethodArgumentNotValidException, NotFoundException {
        salaryService.updateSalaryByDepartment(increment,currency, departmentId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Salary Updated Successfully ",
                        HttpStatus.OK, true));
    }

    @PutMapping(value = "/percent/{percentage}/update-by-department/{departmentId}")
    public ResponseEntity<BaseMessageResponse> updateSalaryByDepartmentPercentage(@PathVariable Long percentage,
                                                                                  @NonNull @PathVariable Long departmentId)
            throws NotFoundException, MethodArgumentNotValidException {
        salaryService.updateSalaryByDepartmentPercentage(percentage, departmentId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Salary Updated Successfully ",
                        HttpStatus.OK, true));
    }

    @PutMapping(value = "/absolute/{increment}/{currency}/update-by-organisation/{organisationId}")
    public ResponseEntity<BaseMessageResponse> updateSalaryByOrganisationAbsolute(@PathVariable Long increment,@PathVariable String currency,
                                                                                  @NonNull @PathVariable Integer organisationId)
            throws  NotFoundException, MethodArgumentNotValidException {
        salaryService.updateSalaryByOrganisation(increment, currency,organisationId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Salary Updated Successfully ",
                        HttpStatus.OK, true));
    }

    @PutMapping(value = "/percent/{percentage}/update-by-organisation/{organisationId}")
    public ResponseEntity<BaseMessageResponse> updateSalaryByOrganisationPercentage(@PathVariable Long percentage,
                                                                                    @NonNull @PathVariable
                                                                                            Integer organisationId)
            throws NotFoundException, MethodArgumentNotValidException {
        salaryService.updateSalaryByOrganisationPercentage(percentage, organisationId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Salary Updated Successfully ",
                        HttpStatus.OK, true));
    }

    @KafkaListener(topics = Constants.TOPIC, groupId = Constants.GROUP_ID,
                   containerFactory = "concurrentKafkaListenerContainerFactory")
    public void updateSalaryKafka(@Valid @RequestBody KafkaDto kafkaDto)
            throws NotFoundException, EmptyBodyException, ConstraintViolationException {
        log.info("Received through kafka{}",kafkaDto);
        salaryService.updateSalaryThroughKafka(kafkaDto);
    }

}

