package com.company.managementservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("currency")
    private String firstName;

    @JsonProperty("from_date")
    private LocalDate fromDate=LocalDate.now();

    @JsonProperty("to_date")
    private LocalDate toDate;

    @JsonProperty("is_current")
    private Boolean isCurrent;


}
