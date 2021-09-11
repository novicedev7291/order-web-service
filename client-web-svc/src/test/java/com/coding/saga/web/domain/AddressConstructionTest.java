package com.coding.saga.web.domain;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AddressConstructionTest {

    @Test
    void shouldMakeNewAddressDefaultForBillingAndShipping() {
        Faker faker = new Faker();
        Street street1 = new Street(faker.address().streetAddress());
        Country country = new Country(faker.address().country());
        State state = new State(faker.address().state());
        PostalCode postalCode = new PostalCode(faker.address().zipCode());
        PhoneNumber phone = new PhoneNumber(faker.address().countryCode(), faker.phoneNumber().phoneNumber());

        Address address = Address.newAddress(
                street1,
                null,
                country,
                state,
                postalCode,
                phone
        );

        assertThat(address.street2())
                .isNotPresent();
        assertThat(address.street1())
                .isEqualTo(street1);
        assertThat(address.country())
                .isEqualTo(country);
        assertThat(address.state())
                .isEqualTo(state);
        assertThat(address.postalCode())
                .isEqualTo(postalCode);
        assertThat(address.phone())
                .isEqualTo(phone);
        assertThat(address.isBilling())
                .isFalse();
        assertThat(address.isShipping())
                .isFalse();
    }

    @Test
    void addressWithOptionalValueStreet1() {
        Faker faker = new Faker();
        Street street1 = new Street(faker.address().streetAddress());
        Street street2 = new Street(faker.address().streetAddress(true));
        Country country = new Country(faker.address().country());
        State state = new State(faker.address().state());
        PostalCode postalCode = new PostalCode(faker.address().zipCode());
        PhoneNumber phone = new PhoneNumber(faker.address().countryCode(), faker.phoneNumber().phoneNumber());

        Address address = Address.newAddress(
                street1,
                street2,
                country,
                state,
                postalCode,
                phone
        );

        assertThat(address.street2())
                .isEqualTo(Optional.of(street2));
    }

    @Test
    void twoAddressWithSameValueMustBeEqual() {
        Faker faker = new Faker();

        String streetValue1 = faker.address().streetAddress();

        String countryValue = faker.address().country();

        String stateValue = faker.address().state();

        String postalCodeValue = faker.address().zipCode();

        String countryCodeValue = faker.address().countryCode();
        String phoneValue = faker.phoneNumber().phoneNumber();

        Street street11 = new Street(streetValue1);
        Country country1 = new Country(countryValue);
        State state1 = new State(stateValue);
        PostalCode postalCode1 = new PostalCode(postalCodeValue);
        PhoneNumber phone1 = new PhoneNumber(countryCodeValue, phoneValue);

        Address address1 = Address.newAddress(
                street11,
                null,
                country1,
                state1,
                postalCode1,
                phone1
        );

        Street street12 = new Street(streetValue1);
        Country country2 = new Country(countryValue);
        State state2 = new State(stateValue);
        PostalCode postalCode2 = new PostalCode(postalCodeValue);
        PhoneNumber phone2 = new PhoneNumber(countryCodeValue, phoneValue);

        Address address2 = Address.newAddress(
                street12,
                null,
                country2,
                state2,
                postalCode2,
                phone2
        );

        assertThat(address1)
                .isEqualTo(address2);

    }

}
