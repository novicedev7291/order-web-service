package com.coding.saga.order;

import com.coding.saga.domain.model.OrderLineItem;
import com.coding.saga.order.entity.Customer;
import com.coding.saga.order.entity.Event;
import com.coding.saga.order.entity.Order;
import com.coding.saga.order.entity.OrderItem;
import com.coding.saga.order.entity.OrderStatus;
import com.coding.saga.order.repository.EventRepository;
import com.coding.saga.order.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static com.coding.saga.order.entity.EventFactory.create;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Service
@RequiredArgsConstructor
@Transactional
class OrderServiceImpl implements OrderService{
    private final OrdersRepository repository;
    private final EventRepository eventRepository;

    @Override
    public Integer add(OrderDto data) {
        CustomerDto customerData = data.getCustomer();
        Customer customer = Customer.builder()
                .customerName(customerData.getName())
                .shippingAddress1(customerData.getAddress1())
                .shippingAddress2(customerData.getAddress2())
                .city(customerData.getCity())
                .state(customerData.getState())
                .country(customerData.getCountry())
                .phone(customerData.getPhone())
                .build();

        List<OrderItem> lineItems = data.getItems().stream()
                .map(this::createLineItem)
                .toList();

        Order order = repository.save(Order.of(customer,lineItems));
        Event orderCreated =
                create(order.getId().toString(),"order", EventType.CREATED, payload(order));
        eventRepository.save(orderCreated);

        return order.getId();
    }

    private OrderItem createLineItem(OrderItemDto itemData) {
        BigDecimal totalSubAmount =
                itemData.getPrice().multiply(BigDecimal.valueOf(itemData.getQty()));
        BigDecimal totalAmount = totalSubAmount.multiply(BigDecimal.valueOf(1.07));

        return OrderItem.of(
                itemData.getItemId(),
                itemData.getPrice(),
                itemData.getQty(),
                totalAmount,
                totalAmount.subtract(totalSubAmount)
        );
    }

    private com.coding.saga.domain.model.Order payload(Order order) {
        Customer customer = order.getCustomer();
        com.coding.saga.domain.model.Customer customerPayload =
                new com.coding.saga.domain.model.Customer(
                        customer.getCustomerName(),
                        customer.getShippingAddress1(),
                        customer.getShippingAddress2(),
                        customer.getCity(),
                        customer.getState(),
                        customer.getCountry(),
                        customer.getPhone()
                );

        List<OrderLineItem> itemsPayload = order.getItems().stream()
                .map(this::lineItemPayload).toList();

        return new com.coding.saga.domain.model.Order(
                order.getId(),
                customerPayload,
                order.getStatus().name(),
                itemsPayload,
                order.getTotalItems(),
                order.getTotalAmount(),
                order.getTotalTax()
        );
    }

    private OrderLineItem lineItemPayload(OrderItem item) {
        return new OrderLineItem(
                item.getItemId(),
                item.getQty(),
                item.getPrice(),
                item.getTotalAmount(),
                item.getTotalTax()
        );
    }


    @Override
    public Integer updateStatus(Integer orderId, OrderStatus status) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id"));
        order.setStatus(status);

        Event orderCancelled =
                create(order.getId().toString(),"order",EventType.CANCELLED,payload(order));

        eventRepository.save(orderCancelled);
        return order.getId();
    }
}
