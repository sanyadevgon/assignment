package com.company.managementservice.constant;


import org.springframework.stereotype.Component;

@Component
public class Constants {


    public static final String ADMIN = "admin";
    public static final String TOPIC="salary";
    public static final String GROUP_ID="employeeSalary";
    public static final Integer EXPIRATION_TIME=3000;
    public static final String TIMESTAMP="yyyy-MM-dd HH:mm:ss";
    public static final String DATE="yyyy-MM-dd";
    public static final String EMAIL="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PHONE="^[1-9][0-9]*${9}";
    public static final String URL="^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.)?$";

    private Constants() {
    }

}