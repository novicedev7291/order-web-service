package com.coding.saga.web.config;

import com.coding.saga.web.domain.CustomerRepository;
import com.coding.saga.web.domain.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Configuration
public class BeanConfig {

    @Bean
    public CustomerService customerService(CustomerRepository repository) {
        return new CustomerService(repository);
    }
}
