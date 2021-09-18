package com.coding.saga.web.adapter.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {
}
