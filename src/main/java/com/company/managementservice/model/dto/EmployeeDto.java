package com.company.managementservice.model.dto;

import com.company.managementservice.model.enums.DesignationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @NotBlank(message = "Please provide a name")
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @NotBlank(message = "Please provide an email")
    @Email
    @JsonProperty("emailId")
    private String emailId;

    @NotBlank(message = "Please provide an address")
    @JsonProperty("address")
    private String address;

    //@Pattern(regexp = "^$|[0-9]{10}")
    @Pattern(regexp = "^[1-9][0-9]*${10}")
    @JsonProperty("phone")
    private String phone;


    @Min(value = 18)
    @JsonProperty("age")
    private Integer age;

    @JsonProperty("designation")
    private DesignationType designationType;

    @JsonProperty("is_active")
    private Boolean isActive = true;

    @JsonProperty("hireDate")
    private LocalDate hireDate = LocalDate.now();

    @JsonProperty("manager_id")
    private Long managerId;

    private Long id;

    @JsonProperty("terminatedDate")//On termination data upadated otherwise null
    private LocalDate terminatedDate;
}
