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
                .antMatchers("/employee/get/{id}").hasAnyRole("SENIOR_MANAGER", "CEO", "SDE1", "SDE2")
                .antMatchers("/employee/add/**").hasAnyRole("SENIOR_MANAGER", "CEO")
                .antMatchers("/employee/remove/**").hasAnyRole("SENIOR_MANAGER", "CEO")
                .antMatchers("/employee/terminate/**").hasAnyRole("SENIOR_MANAGER", "CEO")
                .antMatchers("/employee/update/{id}").hasAnyRole("SENIOR_MANAGER", "CEO")
                .antMatchers("/employee").hasAnyRole("SENIOR_MANAGER", "CEO")
                .antMatchers("/salary/**").hasAnyRole("SENIOR_MANAGER", "CEO")
                .antMatchers("/department/**").hasAnyRole("SENIOR_MANAGER", "CEO")
                .antMatchers("/organisation/**").hasRole("CEO");

    }

}