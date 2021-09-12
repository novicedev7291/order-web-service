package com.coding.saga.web.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryCustomerRepository implements CustomerRepository {
    private Map<CustomerId, Customer> collections = new ConcurrentHashMap<>();

    @Override
    public Customer save(Customer customer) {
        return collections.put(customer.id(), customer);
    }

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        return Optional.ofNullable(collections.get(customerId));
    }
}
