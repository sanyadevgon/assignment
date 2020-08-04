package com.company.managementservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "department")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Department extends AbstractEntity<Long> {

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "manager_id")//doubt for id relation with employee table
    private Long managerId;

    @Column(name="is_active")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Set<Employee> employees;

}
