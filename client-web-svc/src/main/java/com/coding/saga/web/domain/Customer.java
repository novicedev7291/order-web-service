package com.coding.saga.web.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class Customer {
    private final CustomerId id;
    private Name name;
    private final String email;
    private final Set<Address> addresses = new HashSet<>();
    private final Cart cart = new Cart();

    public Customer(CustomerId id, Name name, String email) {
        requireNonNull(email);
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public CustomerId id() {
        return id;
    }

    public Name name() {
        return name;
    }

    public String email() {
        return email;
    }

    public void changeName(Name newName) {
        this.name = new Name(newName.firstName(), newName.lastName());
    }

    public void addAddress(Address address) {
        if (addresses.contains(address)) throw new DuplicateAddressException();
        addresses.add(address.copy());
    }

    public void removeAddress(Address address) {
        if (addresses.contains(address)) {
            addresses.remove(address);
            return;
        }
        throw new AddressNotFoundException();
    }

    public Collection<Address> addresses() {
        return Collections.unmodifiableCollection(addresses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!id.equals(customer.id)) return false;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    public Cart cart() {
        return cart;
    }

    public void defineBilling(Address address) {
        Address matchingAddress = addresses.stream()
                                           .filter(existing -> existing.equals(address))
                                           .findAny()
                                           .orElseThrow(AddressNotFoundException::new);
        Optional<Address> currentBillingAddress = addresses.stream()
                                                           .filter(Address::isBilling)
                                                           .findAny();

        currentBillingAddress.ifPresent(Address::removeAsBilling);
        matchingAddress.markAsBilling();
    }

    void defineShipping(Address address) {
        Address matchingAddress = addresses.stream()
                                    .filter(existing -> existing.equals(address))
                                    .findAny()
                                    .orElseThrow(AddressNotFoundException::new);

        Optional<Address> currentShippingAddress = addresses.stream()
                                                            .filter(Address::isShipping)
                                                            .findAny();
        currentShippingAddress.ifPresent(Address::removeAsShipping);
        matchingAddress.markAsShipping();
    }
}
