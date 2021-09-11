package com.coding.saga.web.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public class Cart {
    private final Set<Item> items = new HashSet<>();

    public Collection<Item> items() {
        return Collections.unmodifiableCollection(items);
    }

    public void addItem(Item item) {
        Optional<Item> possibleDuplicate = items.stream()
                                  .filter(existing -> existing.equals(item))
                                  .findAny();
        possibleDuplicate.ifPresentOrElse(existing -> merge(item, existing), () -> items.add(item));
    }

    private void merge(Item item, Item existing) {
        items.remove(existing);
        items.add(addItemQuantities(existing, item));
    }

    private Item addItemQuantities(Item existing, Item newItem) {
        return new Item(
                existing.productId(),
                existing.price(),
                existing.quantity().add(newItem.quantity())
        );
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Money subTotal() {
        List<Money> lineTotalAmounts = items.stream()
                                  .map(this::totalLineAmount)
                                  .toList();
        return lineTotalAmounts
                .stream()
                .reduce(Money.ZERO, Money::add);
    }

    private Money totalLineAmount(Item item) {
        Money qty = new Money(BigDecimal.valueOf(item.quantity().value()));
        return qty.multiply(item.price());
    }

    public Money tax() {
        return Money.ZERO;
    }
}
