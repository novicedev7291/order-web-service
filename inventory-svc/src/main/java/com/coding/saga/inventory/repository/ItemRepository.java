package com.coding.saga.inventory.repository;

import com.coding.saga.inventory.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
public interface ItemRepository extends JpaRepository<Item,Integer> {
}
