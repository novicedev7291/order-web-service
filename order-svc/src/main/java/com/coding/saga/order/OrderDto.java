package com.coding.saga.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Getter
@Setter
public class OrderDto {
   private CustomerDto customer;
   private List<OrderItemDto> items;
}
