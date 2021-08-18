package com.coding.saga.order;

import com.coding.saga.order.entity.OrderStatus;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public interface OrderService {
    Integer add(OrderDto data);
    Integer updateStatus(Integer orderId, OrderStatus status);
}
