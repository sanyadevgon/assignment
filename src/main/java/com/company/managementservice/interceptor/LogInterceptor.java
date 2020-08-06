package com.company.managementservice.interceptor;




import com.company.managementservice.logging.LoggingService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    LoggingService loggingService;
    Long start;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
         start=System.nanoTime();

        loggingService.logRequest(request,response,handler);

        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        Long end=System.nanoTime();
        log.info(" Total Time taken by Request "+ (end-start)/1000000 +"milli sec");
    }
}
