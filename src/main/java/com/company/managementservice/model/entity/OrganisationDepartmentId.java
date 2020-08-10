package com.company.managementservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationDepartmentId implements Serializable {

    @Column(name = "organisation_id")
    private Long organisationId;

    @Column(name = "department_id")
    private Long departmentId;
}
