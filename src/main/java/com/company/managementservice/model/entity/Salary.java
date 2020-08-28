package com.company.managementservice.model.entity;

import com.company.managementservice.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "salary")
@Data
@NoArgsConstructor

@AllArgsConstructor
public class Salary extends AbstractEntity<Long> implements Serializable {


    @Column(name = "amount")
    private Integer amount;

    @Enumerated
    @Column(name = "currency")
    private CurrencyType currency;

    @Column(name = "from_date")
    private LocalDateTime fromDate;

    @Column(name = "to_date")
    private LocalDateTime toDate;

}
