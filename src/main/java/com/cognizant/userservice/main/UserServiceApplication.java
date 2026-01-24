package com.cognizant.userservice.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.cognizant.userservice.*")
@EnableJpaRepositories(basePackages = "com.cognizant.userservice.repositories")
@EntityScan(basePackages = "com.cognizant.userservice.entities")
@OpenAPIDefinition(
		info=@Info(
				title="User Service REST API Documentation",
				description="REST API Documentation for User Service",
				version="v1.0"
		)
)
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
}
