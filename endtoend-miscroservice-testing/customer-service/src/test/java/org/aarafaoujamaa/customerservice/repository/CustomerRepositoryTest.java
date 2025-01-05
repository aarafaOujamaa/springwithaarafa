package org.aarafaoujamaa.customerservice.repository;

import org.aarafaoujamaa.customerservice.entites.Customer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Annotation {@code @DataJpaTest} utilisée pour les tests de Spring Boot afin de tester uniquement la couche d'accès aux données (repositories).
 * <p>
 * Cette annotation limite le contexte chargé par Spring lors de l'exécution des tests, ce qui :
 * <ul>
 *   <li>Accélère le démarrage en chargeant uniquement les composants liés à Spring Data JPA.</li>
 *   <li>Configure une base de données embarquée (par exemple H2) si aucune autre base n'est spécifiée.</li>
 *   <li>Active un comportement transactionnel, avec rollback automatique à la fin des tests.</li>
 *   <li>Scanne uniquement les beans annotés avec {@code @Repository}.</li>
 * </ul>
 */

@ActiveProfiles("test")
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    public void SetUp() {
        List<Customer> customers = Arrays.asList(
                Customer.builder().firstName("Neta").LastName("Jhony").email("Neta-Jhony@example.com").
                        build(),
                Customer.builder().firstName("Aarafa").LastName("Lina").email("Aarafa-Lina@example.com").
                        build(),
                Customer.builder().firstName("Nota").LastName("Zina").email("Zina-Nota@example.com").
                        build()
        );
        customers.stream().forEach(customer -> customerRepository.save(customer));
    }

    @Test
    public void shouldFindCustomerByEmai() {
        String givenEmail = "Neta-Jhony@example.com";
        Optional<Customer> customer = customerRepository.findByEmail(givenEmail);
        AssertionsForClassTypes.assertThat(customer).isPresent();
    }

    @Test
    public void shouldNotFindCustomerByEmail() {
        String givenEmail = "failedTest@exemple.com";
        AssertionsForClassTypes.assertThat(customerRepository.findByEmail(givenEmail)).isEmpty();
    }

    public void ShouldFindByFirstNameContainsIgnoreCase() {
        final String keyword = "a";

        List<Customer> expected = List.of(
                Customer.builder().firstName("Neta").LastName("Jhony").email("Neta-Jhony@example.com").build(),
                Customer.builder().firstName("Aarafa").LastName("Lina").email("Aarafa-Lina@example.com").build()
        );
        List<Customer> results = customerRepository.findByFirstNameContainsIgnoreCase(keyword);
        AssertionsForClassTypes.assertThat(results).isNotNull();
        AssertionsForClassTypes.assertThat(results.size()).isEqualTo(expected.size());

    }

}