package com.company.managementservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organisation_department")
public class OrganisationDepartment {

    @EmbeddedId
    private OrganisationDepartmentId organisationDepartmentId;

}
