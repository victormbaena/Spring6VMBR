package com.victormbaena.spring6restmvc.controller;

import com.victormbaena.spring6restmvc.entities.Customer;
import com.victormbaena.spring6restmvc.model.CustomerDTO;
import com.victormbaena.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIntegrationTest {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void listCustomerTest() {
        List<CustomerDTO> dtoList = customerController.listCustomer();
        assertThat(dtoList.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void emptyListTest() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtoList = customerController.listCustomer();
        assertThat(dtoList.size()).isEqualTo(0);
    }

    @Test
    void getCustomerByIdTest() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void getCustomerByIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }
}