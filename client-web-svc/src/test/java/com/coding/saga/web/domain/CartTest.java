package com.coding.saga.web.domain;

import org.junit.jupiter.api.Test;

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

        assertThat(cart.subTotal())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(Money.ZERO);
        assertThat(cart.tax())
                .isEqualTo(Money.ZERO);
        assertThat(cart.total())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(Money.ZERO);
    }

    @Test
    void addItemToCart() {
        Item item = createItem(34, 4734.99, 7);

        Cart cart = new Cart();
        cart.addItem(item);

        assertThat(cart.items())
                .isNotEmpty().hasSize(1);
        assertThat(List.copyOf(cart.items()))
                .isEqualTo(List.of(item));

        Money subTotal = Money.of(4734.99 * 7);
        assertThat(cart.subTotal())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(subTotal);
        assertThat(cart.tax())
                .isEqualTo(Money.ZERO);
        assertThat(cart.total())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(subTotal.add(Money.ZERO));
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

        Money subTotal = Money.of(1234.99 * 6);
        assertThat(cart.subTotal())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(subTotal);
        assertThat(cart.tax())
                .isEqualTo(Money.ZERO);
        assertThat(cart.total())
                .isEqualTo(subTotal.add(Money.ZERO));
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

        Money subTotal = Money.of(5289.999 * 2);
        assertThat(cart.subTotal())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(subTotal);
        assertThat(cart.tax())
                .isEqualTo(Money.ZERO);
        assertThat(cart.total())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(subTotal.add(Money.ZERO));
    }

    @Test
    void updateQuantityInCart() {
        Item item1 = createItem(34, 1234.99, 3);
        Item item2 = createItem(41, 5289.999, 2);


        Cart cart = new Cart();
        cart.addItem(item1);
        cart.addItem(item2);

        cart.updateQuantity(ProductId.newId(34), Quantity.of(1));

        assertThat(cart.items())
                .isNotEmpty().hasSize(2);
        assertThat(List.copyOf(cart.items()))
                .isEqualTo(List.of(item1, item2));

        Money subTotal = Money.of(5289.999 * 2).add(Money.of(1234.99));
        assertThat(cart.subTotal())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(subTotal);
        assertThat(cart.tax())
                .isEqualTo(Money.ZERO);
        assertThat(cart.total())
                .usingComparator(Money.twoDecimalComparator())
                .isEqualTo(subTotal.add(Money.ZERO));
    }

    private Item createItem(int productIdValue, double priceValue, int qtyValue) {
        ProductId productId = ProductId.newId(productIdValue);
        Money price = Money.of(priceValue);
        Quantity qty = Quantity.of(qtyValue);
        return new Item(productId, price, qty);
    }
}
