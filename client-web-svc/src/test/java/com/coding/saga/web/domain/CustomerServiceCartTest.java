package com.coding.saga.web.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerServiceCartTest {
    @Test
    void addItemToGivenCustomerCart() {
        CustomerRepository repository = new InMemoryCustomerRepository();
        repository.save(createCustomer(213,"Kuldeep", "Yadav", "ky@abc.com"));

        CustomerService customerService = new CustomerService(repository);

        Item item = new Item(ProductId.newId(123), Money.of(4999.00), Quantity.of(1));

        CustomerId customerId = CustomerId.newId(213);
        customerService.addItemInCart(customerId, item);

        Cart cart = customerService.cartFor(customerId);

        assertThat(cart.items())
                .isNotEmpty().hasSize(1);
        assertThat(cart.subTotal())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(Money.of(4999.00));
    }

    @Test
    void updateItemQuantityForExistingItemInCart() {
        CustomerRepository repository = new InMemoryCustomerRepository();
        repository.save(createCustomer(675, "Sandeep", "Maheswari", "ky@xyz.com"));

        ProductId productId = ProductId.newId(89);
        CustomerId customerId = CustomerId.newId(675);
        Item item = new Item(productId, Money.of(4999.00), Quantity.of(1));

        CustomerService customerService = new CustomerService(repository);
        customerService.addItemInCart(customerId, item);

        customerService.updateItemQuantityInCart(customerId, productId, Quantity.of(2));

        Cart cart = customerService.cartFor(customerId);
        assertThat(cart.items())
                .isNotEmpty().hasSize(1);
        assertThat(cart.subTotal())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(Money.of(4999.00*2));
        assertThat(cart.items())
                .extracting("quantity")
                .isEqualTo(List.of(Quantity.of(2)));
    }

    @Test
    void removeItemFromGivenCustomerCart() {
        CustomerRepository repository = new InMemoryCustomerRepository();
        repository.save(createCustomer(675, "Sandeep", "Maheswari", "ky@xyz.com"));

        CustomerService customerService = new CustomerService(repository);


        ProductId productId1 = ProductId.newId(89);
        Item item1 = new Item(productId1, Money.of(4999.00), Quantity.of(1));

        ProductId productId2 = ProductId.newId(91);
        Item item2 = new Item(productId2, Money.of(3248.59), Quantity.of(7));

        ProductId productId3 = ProductId.newId(124);
        Item item3 = new Item(productId3, Money.of(6765.89), Quantity.of(3));

        CustomerId customerId = CustomerId.newId(675);
        customerService.addItemInCart(customerId, item1);
        customerService.addItemInCart(customerId, item2);
        customerService.addItemInCart(customerId, item3);

        customerService.removeItemFromCart(customerId, productId1);

        Cart cart = customerService.cartFor(customerId);
        assertThat(cart.items())
                .isNotEmpty().hasSize(2);
        assertThat(cart.subTotal())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(Money.of(3248.59*7).add(Money.of(6765.89*3)));
        assertThat(cart.items())
                .extracting("quantity")
                .isEqualTo(List.of(Quantity.of(7), Quantity.of(3)));
    }

    private Customer createCustomer(long id, String firstName, String lastName, String email) {
        CustomerId customerId = CustomerId.newId(id);
        return new Customer(customerId, new Name(firstName, lastName), email);
    }
}
