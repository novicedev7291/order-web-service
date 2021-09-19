package com.coding.saga.web.adapter.out.jpa;

import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerId;
import com.coding.saga.web.domain.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Service
class CustomerJpaRepositoryAdapter implements CustomerRepository {
    private final CustomerJpaRepository repository;

    CustomerJpaRepositoryAdapter(CustomerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity savedCustomer = repository.save(CustomerMapper.toEntityFrom(customer));
        return CustomerMapper.toModelFrom(savedCustomer);
    }

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        Optional<CustomerEntity> optEntity = repository.findById(customerId.value());
        return optEntity.map(CustomerMapper::toModelFrom);
    }

    @Override
    public Optional<Customer> findByEmailId(String email) {
        return repository.findByEmail(email)
                .map(CustomerMapper::toModelFrom);
    }
}
