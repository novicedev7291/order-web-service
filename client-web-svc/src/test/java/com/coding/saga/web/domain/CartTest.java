package com.coding.saga.web.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
class CartTest {

    @Test
    void newCartShouldInitializeEmptyCart() {
        Cart cart = new Cart();
        assertThat(cart.items())
                .isEmpty();
    }

    @Test
    void addItemToCart() {
        Item item = createItem(34, 1234.99, 3);

        Cart cart = new Cart();
        cart.addItem(item);

        assertThat(cart.items())
                .isNotEmpty().hasSize(1);
        assertThat(List.copyOf(cart.items()))
                .isEqualTo(List.of(item));
    }

    @Test
    void addingSameItemMustIncreaseTheQuantity() {
        Item item = createItem(34, 1234.99, 3);

        Cart cart = new Cart();
        cart.addItem(item);
        cart.addItem(item);

        assertThat(cart.items())
                .isNotEmpty().hasSize(1);
        assertThat(cart.items())
                .extracting("quantity")
                .isEqualTo(List.of(Quantity.of(6)));
    }

    @Test
    void removingAnItemMustDisappearFromCart() {
        Item item1 = createItem(34, 1234.99, 3);
        Item item2 = createItem(41, 5289.999, 2);


        Cart cart = new Cart();
        cart.addItem(item1);
        cart.addItem(item2);

        cart.removeItem(item1);

        assertThat(cart.items())
                .isNotEmpty().hasSize(1);
        assertThat(List.copyOf(cart.items()))
                .isEqualTo(List.of(item2));

    }

    @Test
    void addingItemShouldUpdateTheTotalOfCart() {
        Item item1 = createItem(34, 1234.99, 3);
        Item item2 = createItem(41, 5289.999, 2);

        Cart cart = new Cart();
        cart.addItem(item1);
        cart.addItem(item2);

        assertThat(cart.subTotal())
                .usingComparator(twoDecimalComparator())
                .isEqualTo(Money.of(1234.99 * 3).add(Money.of(5289.9999 * 2)));
        assertThat(cart.tax())
                .isEqualTo(Money.ZERO);
    }

    private Comparator<Money> twoDecimalComparator() {
        return (first, second) -> {
            int scale = 2;
            BigDecimal firstValue = first.value().setScale(scale, RoundingMode.HALF_EVEN);
            BigDecimal secondValue = second.value().setScale(scale, RoundingMode.HALF_EVEN);
            return firstValue.compareTo(secondValue);
        };
    }

    private Item createItem(int productIdValue, double priceValue, int qtyValue) {
        ProductId productId = ProductId.newId(productIdValue);
        Money price = Money.of(priceValue);
        Quantity qty = Quantity.of(qtyValue);
        return new Item(productId, price, qty);
    }
}
