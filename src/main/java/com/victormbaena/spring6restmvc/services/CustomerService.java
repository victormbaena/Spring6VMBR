package com.victormbaena.spring6restmvc.services;

import com.victormbaena.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomer();
    Optional<Customer> getCustomerById(UUID customerId);

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID customerId);

    void patchCustomerById(UUID customerId, Customer customer);

}
