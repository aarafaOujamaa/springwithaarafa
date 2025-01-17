package org.aarafaoujamaa.customerservice.repository;

import org.aarafaoujamaa.customerservice.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    List<Customer> findByFirstNameContainsIgnoreCase(String keyword);
}
