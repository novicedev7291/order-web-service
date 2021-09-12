package com.coding.saga.web.domain;

class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void addItemInCart(CustomerId customerId, Item item) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        customer.cart().addItem(item);

        repository.save(customer);
    }

    public Cart cartFor(CustomerId customerId) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        return customer.cart();
    }

    public void updateItemQuantityInCart(CustomerId customerId, ProductId productId, Quantity quantity) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        customer.cart().updateQuantity(productId, quantity);
    }

    public void removeItemFromCart(CustomerId customerId, ProductId productId) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        Item itemToBeRemoved = customer.cart()
                             .items()
                             .stream()
                             .filter(item -> item.productId().equals(productId))
                             .findAny()
                             .orElseThrow(ItemNotFoundException::new);

        customer.cart().removeItem(itemToBeRemoved);
    }
}
