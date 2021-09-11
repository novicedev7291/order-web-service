package com.coding.saga.web.domain;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerConstructionTest {
    @Test
    void newCustomer() {
        CustomerId id = CustomerId.newId(33);
        Name name = new Name("Kuldeep","Yadav");
        String email = "ky@abc.com";
        Customer customer = new Customer(id, name, email);

        assertThat(customer.id())
                .isEqualTo(id);
        assertThat(customer.name())
                .isEqualTo(name);
        assertThat(customer.email())
                .isEqualTo(email);
    }

    @Test
    void addCustomerAddress() {
        CustomerId id = CustomerId.newId(78);
        Name name = new Name("Kuldeep", "Yadav");
        String email = "ky@abc.com";

        Customer customer = new Customer(id, name, email);

        Street street1 = new Street("152, Test Street");
        Country country = new Country("India");
        State state = new State("Delhi");
        PostalCode postalCode = new PostalCode("122001");
        PhoneNumber phone = new PhoneNumber("91","8800337921");
        Address address = Address.newAddress(street1, null, country, state, postalCode, phone);

        customer.addAddress(address);

        assertThat(customer.addresses())
                .isNotEmpty().hasSize(1);
        assertThat(List.copyOf(customer.addresses()))
                .isEqualTo(List.of(address));
    }

    @Test
    void duplicateAddressMustThrowError() {
        CustomerId id = CustomerId.newId(78);
        Name name = new Name("Kuldeep", "Yadav");
        String email = "ky@abc.com";

        Customer customer = new Customer(id, name, email);

        Street street1 = new Street("152, Test Street");
        Country country = new Country("India");
        State state = new State("Delhi");
        PostalCode postalCode = new PostalCode("122001");
        PhoneNumber phone = new PhoneNumber("91","8800337921");

        Address address1 = Address.newAddress(street1, null, country, state, postalCode, phone);
        Address address2 = Address.newAddress(street1, null, country, state, postalCode, phone);


        customer.addAddress(address1);

        assertThrows(DuplicateAddressException.class, () -> customer.addAddress(address2));
    }

    @Test
    void deleteGivenCustomerAddress() {
        CustomerId id = CustomerId.newId(78);
        Name name = new Name("Kuldeep", "Yadav");
        String email = "ky@abc.com";

        Customer customer = new Customer(id, name, email);

        Street street1 = new Street("152, Test Street");
        Country country = new Country("India");
        State state = new State("Delhi");
        PostalCode postalCode = new PostalCode("122001");
        PhoneNumber phone = new PhoneNumber("91","8800337921");

        Address address = Address.newAddress(street1, null, country, state, postalCode, phone);

        customer.addAddress(address);

        customer.removeAddress(address);

        assertThat(customer.addresses())
                .isEmpty();
    }

    @Test
    void throwErrorIfAddressToDeleteIsNotFound() {
        CustomerId id = CustomerId.newId(78);
        Name name = new Name("Kuldeep", "Yadav");
        String email = "ky@abc.com";

        Customer customer = new Customer(id, name, email);

        Street street1 = new Street("152, Test Street");
        Country country = new Country("India");
        State state = new State("Delhi");
        PostalCode postalCode = new PostalCode("122001");
        PhoneNumber phone = new PhoneNumber("91","8800337921");

        Address address = Address.newAddress(street1, null, country, state, postalCode, phone);

        customer.addAddress(address);

        Faker faker = new Faker();
        Street street12 = new Street(faker.address().streetAddress());
        Country country1 = new Country(faker.address().country());
        State state1 = new State(faker.address().state());
        PostalCode postalCode1 = new PostalCode(faker.address().zipCode());
        PhoneNumber phoneNumber = new PhoneNumber(faker.address().countryCode(), faker.phoneNumber().phoneNumber());
        Address addressToRemove = Address.newAddress(street12,null,country1,state1,postalCode1,phoneNumber);

        assertThrows(NotFoundException.class, () -> customer.removeAddress(addressToRemove));
    }

    @Test
    void changeCustomerName() {
        CustomerId id = CustomerId.newId(78);
        Name name = new Name("Kuldeep", "Yadav");
        String email = "ky@abc.com";

        Customer customer = new Customer(id, name, email);

        Faker faker = new Faker();
        Name newName = new Name(faker.name().firstName(), faker.name().lastName());
        customer.changeName(newName);

        assertThat(customer.name())
                .isEqualTo(newName);
    }
}
