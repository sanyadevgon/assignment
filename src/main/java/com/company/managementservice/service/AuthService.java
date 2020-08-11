package com.company.managementservice.service;

import com.company.managementservice.model.entity.Employee;
import com.company.managementservice.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Cacheable(cacheNames = "Authenticator", key = "#firstname")
    public User loadByFirstname(String firstname){
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

    @CacheEvict(cacheNames = "Authenticator", key="#employee.firstName")
    public  void removeAuthcache(Employee employee) {
    }
}
