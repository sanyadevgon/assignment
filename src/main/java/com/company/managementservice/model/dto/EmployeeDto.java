package com.company.managementservice.model.dto;

import com.company.managementservice.model.entity.Salary;
import com.company.managementservice.model.enums.DesignationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto implements Serializable {

    @NotBlank(message = "Please provide a name")
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @NotBlank(message = "Please provide an email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @JsonProperty("emailId")
    private String emailId;

    @NotBlank(message = "Please provide an address")
    @JsonProperty("address")
    private String address;

    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[1-9][0-9]*${9}")
    @JsonProperty("phone")
    private String phone;

    @Min(value = 18)
    @Max(value = 65)
    @JsonProperty("age")
    private Integer age;

    @Enumerated
    @JsonProperty("designation")
    private DesignationType designationType;

    @JsonProperty("is_active")
    private Boolean isActive = true;

    @JsonProperty("hireDate")
    private LocalDate hireDate = LocalDate.now();

    private Long id;

    @JsonProperty("terminatedDate")
    private LocalDate terminatedDate;

}
