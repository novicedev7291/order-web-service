package com.coding.saga.web.domain;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
class CustomerAddressTest {

    @Test
    void selectAddressForBilling() {
        Faker faker = new Faker();

        CustomerId customerId = CustomerId.newId(891);
        Name name = new Name(faker.name().firstName(), faker.name().lastName());

        CustomerRepository repository = new InMemoryCustomerRepository();
        Customer customer = new Customer(customerId, name, faker.internet().safeEmailAddress());

        Address address1 = createAddress(
                faker.address().streetAddress(),
                faker.address().country(),
                faker.address().state(),
                faker.address().zipCode(),
                faker.address().countryCode(),
                faker.phoneNumber().phoneNumber()
        );

        Address address2 = createAddress(
                faker.address().streetAddress(),
                faker.address().country(),
                faker.address().state(),
                faker.address().zipCode(),
                faker.address().countryCode(),
                faker.phoneNumber().phoneNumber()
        );

        customer.addAddress(address1);
        customer.addAddress(address2);

        repository.save(customer);

        CustomerService customerService = new CustomerService(repository);
        customerService.defineBillingAddress(customerId, address2);

        Customer updated = customerService.findById(customerId);
        Optional<Address> optBillingAddress = updated.addresses()
                                                     .stream()
                                                     .filter(Address::isBilling)
                                                     .findAny();

        assertThat(optBillingAddress).isPresent();
    }

    @Test
    void selectAddressForShipping() {
        Faker faker = new Faker();

        CustomerId customerId = CustomerId.newId(891);
        Name name = new Name(faker.name().firstName(), faker.name().lastName());

        CustomerRepository repository = new InMemoryCustomerRepository();
        Customer customer = new Customer(customerId, name, faker.internet().safeEmailAddress());

        Address address1 = createAddress(
                faker.address().streetAddress(),
                faker.address().country(),
                faker.address().state(),
                faker.address().zipCode(),
                faker.address().countryCode(),
                faker.phoneNumber().phoneNumber()
        );

        Address address2 = createAddress(
                faker.address().streetAddress(),
                faker.address().country(),
                faker.address().state(),
                faker.address().zipCode(),
                faker.address().countryCode(),
                faker.phoneNumber().phoneNumber()
        );

        customer.addAddress(address1);
        customer.addAddress(address2);

        repository.save(customer);

        CustomerService customerService = new CustomerService(repository);
        customerService.defineShippingAddress(customerId, address2);

        Customer updated = customerService.findById(customerId);
        Optional<Address> optShippingAddress = updated.addresses()
                                                     .stream()
                                                     .filter(Address::isShipping)
                                                     .findAny();

        assertThat(optShippingAddress).isPresent();
    }

    private Address createAddress(String street1, String country, String state, String postalCode,
                                  String countryCode, String phoneNumber) {
        return Address.newAddress(
                new Street(street1),
                null,
                new Country(country),
                new State(state),
                new PostalCode(postalCode),
                new PhoneNumber(countryCode, phoneNumber)
        );
    }
}
