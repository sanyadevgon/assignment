package com.company.managementservice.controller;


import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.dto.EmployeeDto;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.response.BaseMessageResponse;
import com.company.managementservice.model.response.ServiceResponse;
import com.company.managementservice.service.DepartmentService;
import com.company.managementservice.service.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController()
@RequestMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping(value = "/save")
    public ServiceResponse<?> postEmployeeDetails(@RequestBody EmployeeDto employeeDto) {
        log.info(
                "EmployeeController : save Employee Details : Received Request to save Employee" +
                employeeDto.toString());
        EmployeeDto employeeDto1=employeeService.saveEmployee(employeeDto);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Saved Successfully"+employeeDto1, HttpStatus.OK, true));

    }

    @GetMapping(value = "/{id}")
    public ServiceResponse<?> retrieveEmployee(@PathVariable Long id) throws NotFoundException {
        log.info("EmployeeController : Get Employee Details : Received Request to get Employee", id);
        return new ServiceResponse<>(
                employeeService.getEmployee(id), HttpStatus.OK);

    }

    /*@DeleteMapping("/{id}")
    public ServiceResponse<?> deleteDepartment(@PathVariable long id) throws NotFoundException {

        EmployeeService.deleteEmployee(id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Deleted Successfully", HttpStatus.OK, true));

    }*/

    @PutMapping("/{id}")
    public ServiceResponse<?> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable long id)
            throws NotFoundException {
        log.info("EmployeeController : Put Employee Details : Received Request to put Employee", id);
        employeeService.updateEmployee(employeeDto, id);
        return  new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse("Saved Successfully", HttpStatus.OK, true));

    }



}
