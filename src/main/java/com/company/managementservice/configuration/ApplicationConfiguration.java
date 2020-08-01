package com.company.managementservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfiguration {
    @Value("${spring.datasource.username}")
    private String springUsername;

    @Value("${spring.datasource.password}")
    private String springPassword;

    @Value("${spring.datasource.url}")
    private String springURL;

    @Value("${spring.datasource.driver-class-name}")
    private String springDriver;

    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(springURL);
        dataSource.setUsername(springUsername);
        dataSource.setPassword(springPassword);
        dataSource.setDriverClassName(springDriver);
        return dataSource;
    }
}


