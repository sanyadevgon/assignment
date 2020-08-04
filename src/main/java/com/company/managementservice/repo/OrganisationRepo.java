package com.company.managementservice.repo;

import com.company.managementservice.model.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepo extends JpaRepository<Organisation, Integer> {

   // @Query(value="select d from department d inner join organisation o on d.id=o.id ")


}
