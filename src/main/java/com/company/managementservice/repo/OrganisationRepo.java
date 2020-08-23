package com.company.managementservice.repo;

import com.company.managementservice.model.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationRepo extends JpaRepository<Organisation, Integer> {
    Optional<Organisation> findById(Long organisationId);

}
