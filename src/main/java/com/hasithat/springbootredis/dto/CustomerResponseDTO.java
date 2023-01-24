package com.hasithat.springbootredis.dto;

import com.hasithat.springbootredis.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO implements Serializable {

    private String message;
    private Customer customer;

}
