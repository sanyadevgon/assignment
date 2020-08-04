package com.company.managementservice.controller;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.DepartmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController()
@RequestMapping(value = "/department", consumes = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ServiceResponse<?> saveDepartmentDetails(@Valid @RequestBody DepartmentDto departmentDto) {
        log.info(
                "DepartmentController : saveDepartmentDetails : Received Request to save department Details" +
                departmentDto.toString());
        //DepartmentDto departmentDto1 = departmentService.saveDepartment(departmentDto);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Saved Successfully" + departmentService.saveDepartment(departmentDto).toString(), HttpStatus.OK, true));

    }

    @GetMapping(value = "/{id}")
    public ServiceResponse<?> getDepartmentDetails(@PathVariable Long id) throws NotFoundException {
        log.info("DepartmentController : getDepartmentDetails : Received Request to get Department Details", id);
        return new ServiceResponse<>(
                departmentService.getDepartment(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ServiceResponse<?> putDepartmentDetails(@Valid @RequestBody DepartmentDto departmentDto, @PathVariable long id)
            throws NotFoundException {
        log.info("DepartmentController : putDepartmentDetails : Received Request to put Department Details ", id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Updated Successfully " + departmentService.updateDepartment(departmentDto, id).toString(),
                        HttpStatus.OK, true));
    }

    @PostMapping("/{id}")
    public ServiceResponse<?> postDepartmentInCompany(@Valid @RequestBody DepartmentDto departmentDto, @PathVariable Integer id)
            throws NotFoundException {
        log.info("DepartmentController : postDepartmentInCompany : Received Request to post Department In Company ", id);
        //departmentService.postDepartmentInCompany(departmentDto, id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Saved Successfully" +  departmentService.postDepartmentInCompany(departmentDto, id).toString(), HttpStatus.OK, true));

    }




}
