package com.company.managementservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    @NotNull
    @JsonProperty("description")
    private String description;

    @NotNull
    @JsonProperty("address")
    private String address;

    @JsonProperty("managerId")//doubt for id relation with employee table
    private Long managerId;

    @JsonProperty("is_active")
    private Boolean isActive;

    private Long id;
}
