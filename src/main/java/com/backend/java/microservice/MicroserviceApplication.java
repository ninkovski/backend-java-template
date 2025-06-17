package com.backend.java.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication(
		nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@ComponentScan(
		basePackages = {
				"org.openapitools",
				"com.contract.api" ,
				"org.openapitools.configuration",
				"com.backend.java.microservice.config",
				"com.backend.java.microservice.controller"},
		nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
public class MicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceApplication.class, args);
	}

}
