package com.coding.saga.web.adapter.out.jpa;

import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerId;
import com.coding.saga.web.domain.Name;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
class CustomerMapperTest {

    @Test
    void shouldConvertFromModelToEntity() {
        Customer aCustomer = new Customer(
                CustomerId.newId(23),
                new Name("Kuldeep", "Yadav"),
                "ky@abc.com"
        );

        CustomerEntity entity = CustomerMapper.toEntityFrom(aCustomer);

        assertThat(entity.getId())
                .isEqualTo(23);
        assertThat(entity.getFirstname())
                .isEqualTo("Kuldeep");
        assertThat(entity.getLastname())
                .isEqualTo("Yadav");
        assertThat(entity.getEmail())
                .isEqualTo("ky@abc.com");
    }

    @Test
    void shouldConvertFromEntityToModel() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(478L);
        entity.setFirstname("Kuldeep");
        entity.setLastname("Yadav");
        entity.setEmail("yk@xyz.com");

        Customer aCustomer = CustomerMapper.toModelFrom(entity);

        assertThat(aCustomer.id())
                .isEqualTo(CustomerId.newId(478));
        assertThat(aCustomer.name())
                .isEqualTo(new Name("Kuldeep", "Yadav"));
        assertThat(aCustomer.email())
                .isEqualTo("yk@xyz.com");
    }
}
