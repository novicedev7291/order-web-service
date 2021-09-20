package com.coding.saga.web.domain;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

public class InMemoryCustomerRepository implements CustomerRepository {
    private Map<CustomerId, Customer> collections = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(1);

    @Override
    public Customer save(Customer customer) {
        requireNonNull(customer);

        CustomerId id = customer.id();
        if (Objects.isNull(id)) {
            id = CustomerId.newId(identity.getAndIncrement());
        }

        return collections.put(id, new Customer(id, customer.name(), customer.email()));
    }

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        return Optional.ofNullable(collections.get(customerId));
    }

    @Override
    public Optional<Customer> findByEmailId(String email) {
        return collections.values()
                .stream()
                .filter(customer -> customer.email().equals(email))
                .findAny();
    }
}
