package com.company.managementservice.controller;

import com.company.managementservice.exception.NotFoundException;
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
                new BaseMessageResponse(
                        "Saved Successfully" + departmentService.saveDepartment(departmentDto),
                        HttpStatus.OK, true));

    }

    @GetMapping(value = "/{id}")
    public ServiceResponse<?> getDepartmentDetails(@NonNull @PathVariable Long id) throws NotFoundException {
        log.info("DepartmentController : getDepartmentDetails : Received Request to get Department Details: id{}", id);
        return new ServiceResponse<>(
                departmentService.getDepartment(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ServiceResponse<?> updateDepartmentDetails(@Valid @RequestBody DepartmentDto departmentDto,
                                                   @NonNull @PathVariable long id)
            throws NotFoundException {
        log.info("DepartmentController : putDepartmentDetails : Received Request to put Department Details for id:{}",
                 id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Updated Successfully " + departmentService.updateDepartment(departmentDto, id).toString(),
                        HttpStatus.OK, true));
    }

    @PostMapping("/{id}")
    public ServiceResponse<?> saveDepartmentInCompany(@Valid @RequestBody DepartmentDto departmentDto,
                                                      @NonNull @PathVariable Integer id)
            throws NotFoundException {
        log.info(
                "DepartmentController : postDepartmentInCompany : Received Request to post Department In Company for id:{} ",
                id);
        //departmentService.postDepartmentInCompany(departmentDto, id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Saved Successfully" + departmentService.postDepartmentInCompany(departmentDto, id).toString(),
                        HttpStatus.OK, true));

    }

    @PutMapping(value = "/{companyId}/{departmentId}")
    public ServiceResponse<?> assignDepartmentToCompany(@NonNull @PathVariable Integer companyId, @NonNull @PathVariable Long departmentId
    )
            throws NotFoundException {
        log.info(
                "DepartmentController : assignDepartmentToCompany : Received Request to assign Department To Company:{} :{}"
                , companyId, departmentId);
        departmentService.putDepartmentToOrganisation(companyId,departmentId);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Saved Successfully  ",
                        HttpStatus.OK, true));

    }

    @PutMapping("/{id}/removedepartment")
    public ResponseEntity<?> removeADepartment(@NonNull @PathVariable Long id) throws NotFoundException {
        log.info("OrganisationController : removeDepartmentDetails : Received Request to remove Department Details :{}", id);
        departmentService.removeDepartment(id);
        return new ServiceResponse<BaseMessageResponse>(
                new BaseMessageResponse(
                        "Removed Successfully ",
                        HttpStatus.OK, true));
    }

}
