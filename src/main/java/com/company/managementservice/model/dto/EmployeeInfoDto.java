package com.company.managementservice.model.dto;

import com.company.managementservice.model.enums.DesignationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfoDto {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("emailId")
    private String emailId;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("designation")
    private DesignationType designationType;

    @JsonProperty("isActive")
    private Boolean isActive = true;
}
