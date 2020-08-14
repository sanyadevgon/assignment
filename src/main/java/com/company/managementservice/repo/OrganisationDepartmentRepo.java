package com.company.managementservice.repo;

import com.company.managementservice.model.entity.OrganisationDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrganisationDepartmentRepo extends JpaRepository<OrganisationDepartment,Long> {

    @Transactional
    @Modifying
    @Query(value = "Delete from OrganisationDepartment where department_id=:deptId")
    public Integer removeDepartment(@Param("deptId") Long deptId);

    @Query(value="select count(od) from OrganisationDepartment od where department_id=:deptId and organisation_id=:organId")
    public Integer findOrganisationByDepartment(@Param("deptId") Long deptId,@Param("organId") Integer organId);

    @Query(value="select od from OrganisationDepartment od where department_id=:deptId")
    public List<OrganisationDepartment> updateDepartmentInOrganCache(@Param("deptId") Long deptId);


}
