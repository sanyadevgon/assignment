package com.company.managementservice.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "organisation", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "type"})})
@AllArgsConstructor
@NoArgsConstructor
public class Organisation extends AbstractEntity<Integer> implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "head_office_location")
    private String headOfficeLocation;

    @Column(name = "ceo")
    private Long ceo;

    @Column(name = "url")
    private String url;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id", referencedColumnName = "id")
    private Set<Department> department;

}
