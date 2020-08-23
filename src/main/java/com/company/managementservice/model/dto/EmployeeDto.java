package com.company.managementservice.model.dto;

import com.company.managementservice.constant.Constants;
import com.company.managementservice.model.enums.DesignationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

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
    @Pattern(regexp = Constants.EMAIL)
    @JsonProperty("emailId")
    private String emailId;

    @NotBlank(message = "Please provide an address")
    @JsonProperty("address")
    private String address;

    @Size(min = 10, max = 10)
    @Pattern(regexp = Constants.PHONE)
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

    @JsonFormat(pattern = Constants.DATE)
    @JsonProperty("hireDate")
    private LocalDate hireDate = LocalDate.now();

    private Long id;

    @JsonFormat(pattern = Constants.DATE)
    @JsonProperty("terminatedDate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate terminatedDate;

}
