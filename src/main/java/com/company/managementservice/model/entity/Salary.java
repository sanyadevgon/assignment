package com.company.managementservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "salary")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Salary extends AbstractEntity<Long>{

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne
    Employee employee;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "currency")
    private String firstName;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "is_current")
    private Boolean isCurrent;

}
