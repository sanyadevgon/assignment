package com.company.managementservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "department",uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Department extends AbstractEntity<Long> implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name="is_active")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Employee> employees;



}
