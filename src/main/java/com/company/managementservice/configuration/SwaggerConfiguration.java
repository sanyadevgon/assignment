package com.company.managementservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(
                        "controllerpackagename"))//resquest and resposnebase, ssee personalisation service
                .paths(PathSelectors.any())
                .build();
    }
    /*@ApiResponses({@ApiResponse(code = 201, response = ServiceResponse.class, message = "Created"),
      @ApiResponse(code = 400, response = ServiceResponse.class, message = "Bad Request"),
      @ApiResponse(code = 409, response = ServiceResponse.class, message = "Conflict"),
      @ApiResponse(code = 500, response = ServiceResponse.class, message = "Internal Server Error")
  })*/

}

