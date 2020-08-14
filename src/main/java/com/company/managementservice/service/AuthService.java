package com.company.managementservice.service;

import com.company.managementservice.model.dto.OrganisationDto;
import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.model.entity.Organisation;
import com.company.managementservice.repo.EmployeeRepo;
import com.company.managementservice.repo.OrganisationRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private OrganisationRepo organisationRepo;

    private ModelMapper modelMapper = new ModelMapper();

    @Cacheable(cacheNames = "Authenticator", key = "#firstname")
    public User loadByFirstname(String firstname) {
        return employeeRepo
                .findByfirstName(firstname)
                .map(u -> {
                         String arr[] = new String[1];
                         arr[0] = "ROLE_" + u.getDesignationType().toString().toUpperCase();
                         return new org.springframework.security.core.userdetails.User(
                                 u.getFirstName(),//username
                                 u.getLastName(),//password
                                 u.getIsActive(),
                                 u.getIsActive(),
                                 u.getIsActive(),
                                 u.getIsActive(),
                                 AuthorityUtils.createAuthorityList(arr[0]));
                     }
                ).orElseThrow(() -> new UsernameNotFoundException("No user with "
                                                                  + "the name " + firstname +
                                                                  "was found in the database"));

    }

    @CacheEvict(cacheNames = "Authenticator", key = "#employee.firstName")
    public void removeAuthcache(Employee employee) {
    }

    @CachePut(cacheNames = "organisationU", key = "#organisationId")
    public OrganisationDto addEmployeeToDepartment(Integer organisationId) {
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);
        return modelMapper.map(organisation.get(),OrganisationDto.class);
    }

}
