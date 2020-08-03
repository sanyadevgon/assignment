package com.company.managementservice.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name="organisation")
@AllArgsConstructor
@NoArgsConstructor
public class Organisation  extends AbstractEntity<Integer> {


    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Column(name="head_office_location")
    private String headOfficeLocation;

    @Column(name="ceo")
    private String ceo;//doubt

    @Column(name="url")
    private String url;

    @Column(name="is_active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="organisation_id",referencedColumnName ="id")
    private Set<Department> department;


}
