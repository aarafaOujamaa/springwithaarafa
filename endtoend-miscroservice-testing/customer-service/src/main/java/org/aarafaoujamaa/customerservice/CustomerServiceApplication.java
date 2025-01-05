package org.aarafaoujamaa.customerservice;

import lombok.extern.slf4j.Slf4j;
import org.aarafaoujamaa.customerservice.entites.Customer;
import org.aarafaoujamaa.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}


	@Bean
	@Profile("!test")
	public CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {

		return  agrs -> {

			List<Customer>  customers = Arrays.asList(
					Customer.builder().
							firstName("Neta").
							LastName("Jhony").
							email("Neta-Jhony@example.com").
							build(),
					Customer.builder().firstName("Aarafa").LastName("Lina").email("Aarafa-Lina@example.com").build(),
					Customer.builder().firstName("Nota").LastName("Zina").email("Zina-Nota@example.com").build()
			);
			customers.stream().forEach(customer -> customerRepository.save(customer));
		};
	}

}
