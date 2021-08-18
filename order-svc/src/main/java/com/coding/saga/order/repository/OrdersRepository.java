package com.coding.saga.order.repository;

import com.coding.saga.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public interface OrdersRepository extends JpaRepository<Order, Integer> {
}
