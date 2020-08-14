package com.company.managementservice.model.dto;

import com.company.managementservice.model.entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto implements Serializable {

    @NotBlank(message = "Please provide a name for department")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Please provide a description for department")
    @JsonProperty("address")
    private String address;

    @JsonProperty("is_active")
    private Boolean isActive;

    private Long id;

    private Set<EmployeeDto> employees;
}
