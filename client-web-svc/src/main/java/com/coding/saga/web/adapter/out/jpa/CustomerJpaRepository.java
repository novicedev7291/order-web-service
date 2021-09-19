package com.coding.saga.web.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByEmail(String email);
}
