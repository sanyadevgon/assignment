package com.company.managementservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationDto {

    @NotNull
    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @NotNull
    @JsonProperty("headOfficeLocation")
    private String headOfficeLocation;

    @JsonProperty("ceo")
    private String ceo;//doubt

    @JsonProperty("url")
    private String url;

    @JsonProperty("is_active")
    private Boolean isActive;

    private Integer id;
}
