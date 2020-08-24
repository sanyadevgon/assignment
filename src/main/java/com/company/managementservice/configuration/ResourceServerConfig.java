package com.company.managementservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                .antMatchers("/{employeeId}").hasAnyRole("ADMIN","CEO")
                .antMatchers("/contact-info/**").hasAnyRole("ADMIN","SENIOR_MANAGER", "CEO", "SDE1", "SDE2")
                .antMatchers("/{employeeId}/update-details").hasAnyRole("SENIOR_MANAGER", "CEO","ADMIN")
                .antMatchers("/employee/**").hasAnyRole("ADMIN", "CEO")
                .antMatchers("/{employeeId}/salary/**").hasAnyRole("ADMIN", "CEO")
                .antMatchers("/update-department/{departmentId}/salary").hasAnyRole("ADMIN", "CEO")
                .antMatchers("/update-organisation/{organisationId}/salary").hasAnyRole("ADMIN", "CEO")
                .antMatchers("/department/{departmentId}/remove/**").hasAnyRole("ADMIN")
                .antMatchers("/department/**").hasAnyRole("ADMIN", "CEO")
                .antMatchers("/organisation/{organisationId}/update-details").hasRole("ADMIN")
                .antMatchers("/organisation/{organisationId}/remove-department/{departmentId}").hasRole("ADMIN")
                .antMatchers("/organisation/{id}/remove-organisation").hasRole("ADMIN")
                .antMatchers("/organisation/**").hasAnyRole("ADMIN","CEO")
                .antMatchers("/{id}/**").hasAnyRole("ADMIN","CEO");

    }

}
