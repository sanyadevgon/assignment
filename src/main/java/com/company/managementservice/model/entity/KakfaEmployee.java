package com.company.managementservice.model.entity;

import com.company.managementservice.model.enums.DesignationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakfaEmployee {

    private Long id;

    private Integer amount;

    private String currency;

    private String firstName;

    private String lastName;

    private String emailId;

    private String address;

    private String phone;

    private Integer age;

    @Enumerated
    private DesignationType designationType;

    private Boolean isActive = true;

    private LocalDate hireDate = LocalDate.now();

}
