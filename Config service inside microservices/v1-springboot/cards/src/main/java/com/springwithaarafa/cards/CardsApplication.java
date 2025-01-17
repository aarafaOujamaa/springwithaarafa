package com.springwithaarafa.cards;

import com.springwithaarafa.cards.dto.CardsContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.springwithaarafa.cards.controller") })
@EnableJpaRepositories("com.springwithaarafa.cards.repository")
@EntityScan("com.springwithaarafa.cards.model")*/
@EnableConfigurationProperties(value={CardsContactInfoDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API Documentation",
				description = " Cards microservice REST API Documentation",
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
				description =  "Cards microservice REST API Documentation",
				url = "None"
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
