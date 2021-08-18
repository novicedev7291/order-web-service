package com.coding.saga.order.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Entity
@Table(name = "orders")
@Getter
public class Order extends AbstractEntity<Integer> implements Auditable {
    @Enumerated
    @Setter
    private OrderStatus status = OrderStatus.PROCESSING;

    @Embedded
    private Customer customer;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "orders_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "total_tax")
    private BigDecimal totalTax;

    @Column(name = "total_items")
    private Integer totalItems;

    @Setter
    private Audit audit;

    public static Order of(Customer customer, List<OrderItem> items) {
        requireNonNull(customer, "Customer cannot be null");
        requireNonNull(items, "Items cannot be null or empty");
        if(items.isEmpty()) throw new IllegalArgumentException("Items cannot be null or empty");

        Order order = new Order();
        order.customer = customer;
        order.items = items;
        order.totalAmount = items.stream()
                                 .map(OrderItem::getTotalAmount)
                                 .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.totalTax = items.stream()
                              .map(OrderItem::getTotalTax)
                              .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.totalItems = items.stream()
                                .map(OrderItem::getQty)
                                .reduce(0, Integer::sum);
        return order;
    }
}
