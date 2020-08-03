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

@Log4j2
@RestController()
@RequestMapping(value = "/department", consumes = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping(value = "/save")
    public ServiceResponse<?> postDepartmentDetails(@RequestBody DepartmentDto departmentDto) {
        log.info(
                "DepartmentController : save Department Details : Received Request to save department" +
                departmentDto.toString());
        DepartmentDto departmentDto1=departmentService.saveDepartment(departmentDto);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Saved Successfully"+departmentDto1.toString(), HttpStatus.OK, true));

    }

    @GetMapping(value = "/{id}")
    public ServiceResponse<?> retrieveDepartment(@PathVariable Long id) throws NotFoundException {
        log.info("DepartmentController : Get Department Details : Received Request to get Department", id);
        return new ServiceResponse<>(
                departmentService.getDepartment(id), HttpStatus.OK);

    }

    /*@DeleteMapping("/{id}")
    public ServiceResponse<?> deleteDepartment(@PathVariable long id) throws NotFoundException {

        departmentService.deleteDepartment(id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Deleted Successfully", HttpStatus.OK, true));

    }*/

    @PutMapping("/{id}")
    public ServiceResponse<?> updateDepartment(@RequestBody DepartmentDto departmentDto, @PathVariable long id)
            throws NotFoundException {
        log.info("DepartmentController : Put Department Details : Received Request to put Department", id);
        departmentService.updateDepartment(departmentDto, id);
        return  new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Saved Successfully", HttpStatus.OK, true));

    }



}
