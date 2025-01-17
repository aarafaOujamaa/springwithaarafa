package com.springwithaarafa.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.springwithaarafa.loans.controller") })
@EnableJpaRepositories("com.springwithaarafa.loans.repository")
@EntityScan("com.springwithaarafa.loans.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = " Loans microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Aarafa Oujamaa",
						email = "aarafa.oujamaa@gmail.com",
						url = "https://www.linkedin.com/in/aarafa-oujamaa-6226098b/"
				),
				license = @License(
						name = "Apache 2.0",
						url="https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Loans microservice REST API Documentation",
				url = "None"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}
}