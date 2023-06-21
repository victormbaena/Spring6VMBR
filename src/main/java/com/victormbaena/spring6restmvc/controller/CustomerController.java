package com.victormbaena.spring6restmvc.controller;

import com.victormbaena.spring6restmvc.model.CustomerDTO;
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
public class CustomerController {
    private final CustomerService customerService;
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> listCustomer() {
        log.info("Get Customer list - In controller");
        List<CustomerDTO> customerList = customerService.listCustomer();
        log.info("Customer number of elements = " + (long) customerList.size());
        return customerList;
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable UUID customerId) {
        log.debug("Get customer by ID - In controller - Customer");
        CustomerDTO customer = customerService.getCustomerById(customerId)
                .orElseThrow(NotFoundException::new);
        log.info(customer.toString());
        return customer;
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<HttpStatus> handlePost(@RequestBody CustomerDTO customer) {
        log.info("Received customer: " + customer.toString());
        CustomerDTO customerSaved = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + customerSaved.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> updateById(@PathVariable("customerId") UUID customerId,
                                                 @RequestBody CustomerDTO customer) {
        if (customerService.updateCustomerById(customerId, customer).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("customerId") UUID customerId) {
        if (!customerService.deleteCustomerById(customerId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<HttpStatus> patchCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer) {
        customerService.patchCustomerById(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
