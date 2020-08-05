package com.company.managementservice.model.dto;

import com.company.managementservice.model.entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DepartmentDto {

    @NotBlank(message = "Please provide a name for department")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Please provide a description for department")
    @JsonProperty("address")
    private String address;


    @JsonProperty("is_active")
    private Boolean isActive;

    private Long id;

    private Set<Employee> employees;
}
