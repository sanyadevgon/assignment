package com.company.managementservice.logging;



import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface LoggingService {

    void logRequest(HttpServletRequest httpServletRequest,HttpServletResponse response, Object body);

    void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);
}