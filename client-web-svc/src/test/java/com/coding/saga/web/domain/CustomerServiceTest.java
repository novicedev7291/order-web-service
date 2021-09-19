package com.coding.saga.web.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
class CustomerServiceTest {

    @Test
    void shouldFindCustomerByEmailId() {
        String email = "ky@abc.com";
        Name name = new Name("Kuldeep", "Yadav");
        CustomerId id = CustomerId.newId(67);

        Customer aCustomer = new Customer(id, name, email);

        InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        repository.save(aCustomer);

        CustomerService service = new CustomerService(repository);
        Optional<Customer> customerOpt = service.findByEmailId(email);

        assertThat(customerOpt).isPresent();

        Customer customer = customerOpt.get();
        assertThat(customer.id())
                .isEqualTo(id);
        assertThat(customer.name())
                .isEqualTo(name);
        assertThat(customer.email())
                .isEqualTo(email);
    }

    @Test
    void shouldReturnEmptyWhenCustomerNotFoundWithEmail() {
        String email = "ky@abc.com";
        Name name = new Name("Kuldeep", "Yadav");
        CustomerId id = CustomerId.newId(67);

        Customer aCustomer = new Customer(id, name, email);

        InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        repository.save(aCustomer);

        CustomerService service = new CustomerService(repository);
        Optional<Customer> customerOpt = service.findByEmailId("yk@abc.com");

        assertThat(customerOpt).isNotPresent();
    }

}
