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

    /*
    * In below methods value attribute means cache name. This can be common for
    * all methods. If we provide only cachename and not id value then  redis
    * will generate a default id value while storing data in the cache.
    * */

    @Autowired
    CustomerRepository customerRepository;

    /* Below annotation means while saving a customer, delete customers cache completely.
     * */
    @CacheEvict(value = "customers", allEntries = true)
    //@CachePut(value = "customers")
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /*@Cacheable annotation is used to enable caching for below method.
     */
    @Cacheable(value = "customers")
    public List<Customer> getAllCustomers() {
        Iterable<Customer> all = customerRepository.findAll();
        List<Customer> customers=null;
        if(all!=null){
            customers=(List<Customer>) all;

        }
        return customers;
    }

    @Cacheable(value = "customers", key = "#id")
    //@Cacheable(value = "customers")
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
    /*Below annotation means while deleting a customer, delete relevant customer cache completely
    using id as the key.
     */
    @CacheEvict(value = "customers", key = "#id")
    public long deleteCustomer(int id) {
        customerRepository.deleteById(id);
        return 1;
    }

    /* Below annotation means while updating a customer, delete relevant customer cache completely
    using id as the key.
    */
    @CacheEvict(value = "customers", key = "#id")
    //@CachePut(value = "customers")
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
