package com.company.managementservice.model.dto;

import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.enums.DesignationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @NotNull
    @JsonProperty("emailId")
    private String emailId;

    @NotNull
    @JsonProperty("address")
    private String address;

    //designation ENUMS,  status ENUM*

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("designation")
    private DesignationType designationType;

    @JsonProperty("is_active")
    private Boolean isActive=true;

    @JsonProperty("hireDate")
    private LocalDate hireDate=LocalDate.now();

    @JsonProperty("manager_id")
    private Long managerId;

    private Long id;

   /* @JsonProperty("terminatedDate")//On termination data upadated otherwise null
    private LocalDate terminatedDate;*/
}
