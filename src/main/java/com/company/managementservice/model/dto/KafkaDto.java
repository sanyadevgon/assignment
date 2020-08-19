package com.company.managementservice.model.dto;


import com.company.managementservice.model.enums.DesignationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaDto implements Serializable {


    //@NotBlank(message = "Please provide a name")
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @Enumerated
    @JsonProperty("designation")
    private DesignationType designationType;


    private Long id;

    @Min(value=0)
    @JsonProperty("amount")
    private Integer amount;

    @Enumerated
    @JsonProperty("currency")
    private String currency;



}
