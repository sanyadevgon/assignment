/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.managementservice.service;

import com.company.managementservice.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String firstname) throws UsernameNotFoundException {

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

}
