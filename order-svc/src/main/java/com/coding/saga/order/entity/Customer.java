package com.coding.saga.order.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Customer implements Serializable {
    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_shipping_address1")
    private String shippingAddress1;

    @Column(name = "customer_shipping_address2")
    private String shippingAddress2;

    @Column(name = "customer_city")
    private String city;

    @Column(name = "customer_state")
    private String state;

    @Column(name = "customer_country")
    private String country;

    @Column(name = "customer_phone")
    private String phone;
}
