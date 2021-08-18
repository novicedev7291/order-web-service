package com.coding.saga.order.repository;

import com.coding.saga.order.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public interface EventRepository extends JpaRepository<Event, String> {
}
