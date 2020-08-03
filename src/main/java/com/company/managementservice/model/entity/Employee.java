package com.company.managementservice.model.entity;

import com.company.managementservice.model.enums.DesignationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
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

    //designation ENUMS,  status ENUM*

    @Column(name = "phone")
    private String phone;

    @Enumerated
    private DesignationType designationType;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "hire_date")
    private LocalDate hireDate=LocalDate.now();

    @Column(name = "terminated_date")//if status is active else teminated
    private LocalDate terminatedDate;
//mappingContext'; nested exception is org.springframework.beans.factory.BeanCreationException:
// Error creating bean with name 'jpaMappingContext': Invocation of init method failed;
// nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default]
// Unable to build Hibernate SessionFactory; nested exception is org.hibernate.MappingException: Could
    @Column(name = "manager_id")
    private long managerId;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Salary> salaries;

}
