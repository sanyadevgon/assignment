package com.company.managementservice.model.dto;

import com.company.managementservice.model.enums.CurrencyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonFormat(pattern = "yyyy-MM-dd ")
    @JsonProperty("from_date")
    private LocalDate fromDate = LocalDate.now();

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("to_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate toDate;

}
