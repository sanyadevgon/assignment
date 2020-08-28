package com.company.managementservice.model.dto;

import com.company.managementservice.constant.Constants;
import com.company.managementservice.model.enums.CurrencyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto implements Serializable {

    @Min(value=0)
    @NonNull
    @JsonProperty("amount")
    private Integer amount;

    @Enumerated
    @JsonProperty("currency")
    private String currency;

    @JsonFormat(pattern = Constants.TIMESTAMP)
    @JsonProperty("from_date")
    private LocalDateTime fromDate = LocalDateTime.now();

    @JsonFormat(pattern = Constants.TIMESTAMP)
    @JsonProperty("to_date")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime toDate;

}
