package com.coding.saga.order;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Getter
@Setter
class CustomerDto {
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String phone;
}
