package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
import com.company.managementservice.model.dto.DepartmentDto;
import com.company.managementservice.model.dto.OrganisationDto;
import com.company.managementservice.model.entity.Department;
import com.company.managementservice.repo.DepartmentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        Department department=departmentRepo.save(modelMapper.map(departmentDto, Department.class));
        departmentDto.setId(department.getId());
        return modelMapper.map(department, DepartmentDto.class);
    }

    public DepartmentDto getDepartment(Long id) throws NotFoundException {

        Optional<Department> department = departmentRepo.findById(id);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + id);
        return modelMapper.map(department.get(), DepartmentDto.class);
    }

   /* public void deleteDepartment(Long id) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(id);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + id);
        departmentRepo.deleteById(id);
    }*/

    public DepartmentDto updateDepartment(DepartmentDto departmentDto, long id) throws NotFoundException {
        Optional<Department> department = departmentRepo.findById(id);
        if (!department.isPresent())
            throw new NotFoundException("NOT FOUND department id-" + id);
        Department departmentInfo = modelMapper.map(departmentDto, Department.class);
        departmentInfo.setId(id);
        departmentInfo.setCreatedAt(department.get().getCreatedAt());
        departmentInfo.setCreatedBy(department.get().getCreatedBy());
        departmentRepo.save(departmentInfo);
        return modelMapper.map(departmentInfo, DepartmentDto.class);

    }
}
