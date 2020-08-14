package com.company.managementservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto implements Serializable {

    @Min(value=0)
    @JsonProperty("amount")
    private Integer amount;

    @Enumerated
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("from_date")
    private LocalDate fromDate = LocalDate.now();

    @JsonProperty("to_date")
    private LocalDate toDate;

}
