package com.company.managementservice.model.dto;

import com.company.managementservice.model.enums.CurrencyType;
import com.company.managementservice.model.enums.IncrementType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryUpdateDto implements Serializable {

    @JsonProperty("value")
    private Long value;

    @Enumerated()
    @JsonProperty("currency")
    private String currency;

    @Enumerated
    @JsonProperty("incrementType")
    private String incrementType;

}
