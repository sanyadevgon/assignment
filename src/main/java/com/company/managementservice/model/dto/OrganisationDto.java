package com.company.managementservice.model.dto;

import com.company.managementservice.model.entity.Department;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
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
    private Long ceo;//doubt

    @JsonProperty("url")
    private String url;

    @JsonProperty("is_active")
    private Boolean isActive;

    private Integer id;

    private Set<Department> department;
}
