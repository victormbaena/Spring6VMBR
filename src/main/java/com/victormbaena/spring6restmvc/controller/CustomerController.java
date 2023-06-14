package com.victormbaena.spring6restmvc.controller;

import com.victormbaena.spring6restmvc.model.Customer;
import com.victormbaena.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomer() {
        log.info("Get Customer list - In controller");
        List<Customer> customerList = customerService.listCustomer();
        log.info("Customer number of elements = " + (long) customerList.size());
        return customerList;
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable UUID customerId) {
        log.debug("Get customer by ID - In controller - Customer");
        Customer customer = customerService.getCustomerById(customerId);
        log.info(customer.toString());
        return customer;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> handlePost(@RequestBody Customer customer) {
        log.info("Received customer: " + customer.toString());
        Customer customerSaved = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + customerSaved.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<HttpStatus> updateById(@PathVariable("customerId") UUID customerId,
                                     @RequestBody Customer customer) {

        customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{customerId}")
    public ResponseEntity<HttpStatus> patchCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
        customerService.patchCustomerById(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
