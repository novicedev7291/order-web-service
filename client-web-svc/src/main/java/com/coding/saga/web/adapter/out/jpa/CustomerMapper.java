package com.coding.saga.web.adapter.out.jpa;

import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerId;
import com.coding.saga.web.domain.Name;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
class CustomerMapper {
    static CustomerEntity toEntityFrom(Customer aCustomer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(aCustomer.id().value());
        entity.setFirstname(aCustomer.name().firstName());
        entity.setLastname(aCustomer.name().lastName());
        entity.setEmail(aCustomer.email());

        return entity;
    }

    static Customer toModelFrom(CustomerEntity entity) {
        return new Customer(
                CustomerId.newId(entity.getId()),
                new Name(entity.getFirstname(), entity.getLastname()),
                entity.getEmail()
        );
    }
}
