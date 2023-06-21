package com.victormbaena.spring6restmvc.controller;

import com.victormbaena.spring6restmvc.entities.Customer;
import com.victormbaena.spring6restmvc.mappers.CustomerMapper;
import com.victormbaena.spring6restmvc.model.CustomerDTO;
import com.victormbaena.spring6restmvc.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class CustomerControllerIntegrationTest {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

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

    @Rollback
    @Transactional
    @Test
    void saveNewCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Customer")
                .build();

        ResponseEntity<HttpStatus> responseEntity = customerController.handlePost(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = Objects.requireNonNull(responseEntity.getHeaders().getLocation())
                .getPath()
                .split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Customer customer = customerRepository.findById(savedUUID).orElse(Customer.builder().build());
        assertThat(customer).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void updateExistingCustomerTest() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "UPDATED";
        customerDTO.setCustomerName(customerName);

        ResponseEntity<HttpStatus> responseEntity = customerController.updateById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).orElse(Customer.builder().build());
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Test
    void updateNotFoundCustomerTest() {
        assertThrows(NotFoundException.class,
                () -> customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFoundCustomerTest() {
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity<HttpStatus> responseEntity = customerController.deleteById(customer.getId());
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Test
    void deleteByIdNotFoundCustomerTest() {
        assertThrows(NotFoundException.class, () ->
                customerController.deleteById(UUID.randomUUID()));
    }

    @Transactional
    @Rollback
    @Test
    void patchByIdFoundCustomerTest() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        log.info(customer.getCustomerName());
        final String customerName = "UPDATED";
        customerDTO.setCustomerName(customerName);

        ResponseEntity<HttpStatus> responseEntity = customerController.patchCustomerById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).orElse(null);
        assert updatedCustomer != null;
        log.info(updatedCustomer.getCustomerName());
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
    }
}