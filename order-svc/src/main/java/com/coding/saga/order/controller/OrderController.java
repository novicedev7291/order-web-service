package com.coding.saga.order.controller;

import com.coding.saga.order.OrderDto;
import com.coding.saga.order.OrderService;
import com.coding.saga.order.entity.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@RequiredArgsConstructor
@RestController
class OrderController {
    private final OrderService service;

    @PostMapping("/orders")
    public ResponseEntity<IdResponse<Integer>> create(@RequestBody OrderDto order) {
        IdResponse<Integer> resp = new IdResponse<>(service.add(order));
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<IdResponse<Integer>> cancel(@PathVariable Integer orderId) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new IdResponse<>(service.updateStatus(orderId, OrderStatus.CANCELLED)));
    }

}
