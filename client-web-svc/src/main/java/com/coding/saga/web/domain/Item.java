package com.coding.saga.web.domain;

public record Item(ProductId productId, Money price, Quantity quantity) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return productId.equals(item.productId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
