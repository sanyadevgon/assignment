package com.company.managementservice.model.entity;

import com.company.managementservice.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "salary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary extends AbstractEntity<Long> implements Serializable {


    @Column(name = "amount")
    private Integer amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

}
