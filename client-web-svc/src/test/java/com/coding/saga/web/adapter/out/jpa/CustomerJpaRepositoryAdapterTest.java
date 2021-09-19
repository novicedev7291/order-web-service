package com.coding.saga.web.adapter.out.jpa;

import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerId;
import com.coding.saga.web.domain.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@ActiveProfiles("test")
@DataJpaTest
class CustomerJpaRepositoryAdapterTest {
    @Autowired
    private CustomerJpaRepository repository;

    @Test
    void shouldSaveCustomerModelToDB() {
        Name name = new Name("Kuldeep", "Yadav");
        String email = "yk@pqrs.com";

        Customer aCustomer = new Customer(null, name, email);

        Customer savedCustomer = new CustomerJpaRepositoryAdapter(repository)
                .save(aCustomer);

        assertThat(savedCustomer.id())
                .isNotNull();
        assertThat(savedCustomer.name())
                .isEqualTo(name);
        assertThat(savedCustomer.email())
                .isEqualTo(email);
    }

    @Test
    void shouldLoadCustomerFromRepository() {
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstname("Kuldeep");
        entity.setLastname("Yadav");
        entity.setEmail("yk@xyz.com");

        entity = repository.save(entity);

        CustomerId customerId = CustomerId.newId(entity.getId());
        Optional<Customer> possibleCustomer = new CustomerJpaRepositoryAdapter(repository)
                .findById(customerId);

        assertThat(possibleCustomer).isPresent();

        Customer customer = possibleCustomer.get();
        assertThat(customer.id())
                .isEqualTo(customerId);
        assertThat(customer.name())
                .isEqualTo(new Name("Kuldeep", "Yadav"));
        assertThat(customer.email())
                .isEqualTo("yk@xyz.com");
    }

    @Test
    void shouldGiveEmptyCustomerWhenNotFoundInDB() {
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstname("Kuldeep");
        entity.setLastname("Yadav");
        entity.setEmail("yk@xyz.com");

        repository.save(entity);

        CustomerId customerId = CustomerId.newId(56);
        Optional<Customer> possibleCustomer = new CustomerJpaRepositoryAdapter(repository)
                .findById(customerId);

        assertThat(possibleCustomer).isNotPresent();
    }

    @Test
    void shouldLoadCustomerWithGivenEmailId() {
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstname("Kuldeep");
        entity.setLastname("Yadav");
        entity.setEmail("yk@xyz.com");

        repository.save(entity);

        Optional<Customer> possibleCustomer = new CustomerJpaRepositoryAdapter(repository)
                .findByEmailId("yk@xyz.com");

        assertThat(possibleCustomer)
                .isPresent();

        Customer customer = possibleCustomer.get();

        assertThat(customer.email())
                .isEqualTo("yk@xyz.com");

    }

    @Test
    void shouldGiveEmptyCustomerWithGivenEmailId() {
        Optional<Customer> possibleCustomer = new CustomerJpaRepositoryAdapter(repository)
                .findByEmailId("yk@xyz.com");

        assertThat(possibleCustomer)
                .isNotPresent();

    }

}
