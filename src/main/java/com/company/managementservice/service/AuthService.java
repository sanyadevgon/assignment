package com.company.managementservice.service;

import com.company.managementservice.exception.NotFoundException;
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

    @Cacheable(cacheNames = "Authenticator", key = "#employeeId")
    public User loadByFirstname(String employeeId) {
        return employeeRepo
                .findById(Long.valueOf(employeeId))
                .map(u -> {
                         String arr[] = new String[1];
                         arr[0] = "ROLE_" + u.getDesignationType().toString().toUpperCase();
                         return new org.springframework.security.core.userdetails.User(
                                 String.valueOf(u.getId()),//username
                                 u.getLastName(),//password
                                 u.getIsActive(),
                                 u.getIsActive(),
                                 u.getIsActive(),
                                 u.getIsActive(),
                                 AuthorityUtils.createAuthorityList(arr[0]));
                     }
                ).orElseThrow(() -> new UsernameNotFoundException("No user with "
                                                                  + "the name " + employeeId +
                                                                  "was found in the database"));

    }

    @CacheEvict(cacheNames = "Authenticator", key = "#employee.id")
    public void removeAuthcache(Employee employee) {
    }

    @CachePut(cacheNames = "organisationU", key = "#organisationId")
    public OrganisationDto addEmployeeToDepartment(Integer organisationId) throws NotFoundException {
        Optional<Organisation> organisation = organisationRepo.findById(organisationId);
        if(!organisation.isPresent()){
            throw  new NotFoundException("Not found organisation");
        }
        return modelMapper.map(organisation.get(),OrganisationDto.class);
    }

}
