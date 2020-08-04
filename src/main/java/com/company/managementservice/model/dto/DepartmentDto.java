package com.company.managementservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DepartmentDto {

    @NotBlank(message = "Please provide a description for department")
    @JsonProperty("description")
    private String description;

    @NotBlank(message = "Please provide a description for department")
    @JsonProperty("address")
    private String address;

    @JsonProperty("managerId")//doubt for id relation with employee table
    private Long managerId;

    @JsonProperty("is_active")
    private Boolean isActive;

    private Long id;
}
