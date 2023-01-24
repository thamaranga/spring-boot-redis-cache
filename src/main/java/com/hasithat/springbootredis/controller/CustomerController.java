package com.hasithat.springbootredis.controller;

import com.hasithat.springbootredis.dto.CustomerResponseDTO;
import com.hasithat.springbootredis.entity.Customer;
import com.hasithat.springbootredis.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/{id}")
    public long deleteCustomer(@PathVariable int id) {
        return customerService.deleteCustomer(id);
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }
}
