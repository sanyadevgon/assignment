package com.company.managementservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrganisationDto implements Serializable {

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

    @Pattern(
            regexp = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.)?$")
    @JsonProperty("url")
    private String url;

    @JsonProperty("is_active")
    private Boolean isActive=true;

    private Integer id;

    private Set<DepartmentDto> department;
}
