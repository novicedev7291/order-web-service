package com.coding.saga.web.domain;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findById(CustomerId customerId);
}
