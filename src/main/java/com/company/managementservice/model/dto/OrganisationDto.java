package com.company.managementservice.model.dto;

import com.company.managementservice.model.entity.Department;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrganisationDto {

    @NotBlank(message = "Please provide a name")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Please provide a type for organisation")
    @JsonProperty("type")
    private String type;

    @NotBlank(message = "Please provide a location")
    @JsonProperty("headOfficeLocation")
    private String headOfficeLocation;

    @JsonProperty("ceo")
    private Long ceo;

    @JsonProperty("url")
    private String url;

    @JsonProperty("is_active")
    private Boolean isActive;

    private Integer id;

    private Set<Department> department;
}
