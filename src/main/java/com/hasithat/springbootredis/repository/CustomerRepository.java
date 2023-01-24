package com.hasithat.springbootredis.repository;

import com.hasithat.springbootredis.entity.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
