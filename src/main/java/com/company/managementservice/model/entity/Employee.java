package com.company.managementservice.model.entity;

import com.company.managementservice.model.enums.DesignationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="employee", uniqueConstraints = {@UniqueConstraint(columnNames={"first_name","phone"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends AbstractEntity<Long> implements Serializable {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name="age")
    private Integer age;

    @Enumerated
    private DesignationType designationType;

    @Column(name = "is_active")
    private Boolean isActive;

    @JsonIgnore
    @Column(name = "hire_date")
    private LocalDate hireDate=LocalDate.now();

    @Column(name = "terminated_date")//if status is active else terminated
    private LocalDate terminatedDate;


    @JoinColumn(name="employee_id", referencedColumnName ="id")
    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true,cascade = CascadeType.PERSIST)
    private Set<Salary> salaries;

}
