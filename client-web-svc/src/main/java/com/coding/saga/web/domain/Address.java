package com.coding.saga.web.domain;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class Address {
    private final Street street1;
    private Street street2;
    private final Country country;
    private final State state;
    private final PostalCode postalCode;
    private final PhoneNumber phone;
    private boolean isBilling = false;
    private boolean isShipping = false;

    private Address(Street street1, Street street2, Country country,
                    State state, PostalCode postalCode, PhoneNumber phone) {
        this.street1 = street1;
        this.street2 = street2;
        this.country = country;
        this.state = state;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public static Address newAddress(Street street1, Street street2, Country country, State state,
                                     PostalCode postalCode, PhoneNumber phone) {
        requireNonNull(street1);
        requireNonNull(country);
        requireNonNull(state);
        requireNonNull(postalCode);
        requireNonNull(phone);
        return new Address(
                street1,
                street2,
                country,
                state,
                postalCode,
                phone
        );
    }

    public boolean isBilling() {
        return isBilling;
    }

    public boolean isShipping() {
        return isShipping;
    }

    public Street street1() {
        return street1;
    }

    public Optional<Street> street2() {
        return Optional.ofNullable(street2);
    }

    public Country country() {
        return country;
    }

    public State state() {
        return state;
    }

    public PostalCode postalCode() {
        return postalCode;
    }

    public PhoneNumber phone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return address.street1.equals(street1)
                && address.country.equals(country)
                && address.state.equals(state)
                && address.postalCode.equals(postalCode);
    }

    @Override
    public int hashCode() {
        int result = street1.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + postalCode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street1=" + street1 +
                ", country=" + country +
                ", state=" + state +
                ", postalCode=" + postalCode +
                '}';
    }

    public Address copy() {
        return newAddress(
                new Street(street1.value()),
                street2 == null? null : new Street(street2.value()),
                new Country(country.value()),
                new State(state.value()),
                new PostalCode(postalCode.value()),
                new PhoneNumber(phone.countryCode(), phone.value())
        );
    }
}
