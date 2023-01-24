package com.hasithat.springbootredis.service;

import com.hasithat.springbootredis.dto.CustomerResponseDTO;
import com.hasithat.springbootredis.entity.Customer;
import com.hasithat.springbootredis.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {



    @Autowired
    CustomerRepository customerRepository;

    /* Below annotation means while saving a customer, delete customers cache completely.
     * */
    @Caching(evict = {
            @CacheEvict(value = "customers", allEntries = true)})
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /*@Cacheable annotation is used to enable caching for below method.
     */
    //@Cacheable(cacheNames = "customers")
    @Cacheable(value = "customers")
    public List<Customer> getAllCustomers() {
        Iterable<Customer> all = customerRepository.findAll();
        List<Customer> customers=null;
        if(all!=null){
            customers=(List<Customer>) all;

        }
        return customers;
    }
    //@Cacheable(cacheNames = "customer", key = "#id")
    @Cacheable(value = "customer", key = "#id")
    public CustomerResponseDTO getCustomerById(int id) {
        Optional<Customer> optionalCustomer=customerRepository.findById(id);
        CustomerResponseDTO customerResponseDTO= new CustomerResponseDTO();
        if(optionalCustomer.isPresent()){
            customerResponseDTO.setMessage("success");
            customerResponseDTO.setCustomer(optionalCustomer.get());
        }else{
            customerResponseDTO.setMessage("no customer data found");
        }
        return customerResponseDTO;
    }
    /*Below annotation means while deleting a customer, delete customers cache completely
    and delete only relevant customer from customer cache using id as the key.
     */
    @Caching(evict = {
            @CacheEvict(value = "customer", key = "#id"),
            @CacheEvict(value = "customers", allEntries = true)})
    public long deleteCustomer(int id) {
        customerRepository.deleteById(id);
        return 1;
    }

    /* Below annotation means while updating a customer, delete customers cache completely
    and delete only relevant customer from customer cache using id as the key.
    */
    @Caching(evict = {
            @CacheEvict(value = "customer", key = "#id"),
            @CacheEvict(value = "customers", allEntries = true)})
    public CustomerResponseDTO updateCustomer(int id, Customer customer) {
       CustomerResponseDTO existing=this.getCustomerById(id);
       CustomerResponseDTO result= new CustomerResponseDTO();
       if(existing.getCustomer()!=null){
           existing.getCustomer().setLastName(customer.getLastName());
           existing.getCustomer().setDob(customer.getDob());
           existing.getCustomer().setFirstName(customer.getFirstName());
           existing.getCustomer().setEmail(customer.getEmail());
           existing.getCustomer().setPhone(customer.getPhone());
           Customer savedCustomer=customerRepository.save(existing.getCustomer());
           result.setMessage("success");
           result.setCustomer(savedCustomer);
       }else{
           result.setMessage("No customer data found for update");
       }
       return result;
    }
}
