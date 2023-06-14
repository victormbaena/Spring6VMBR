package com.victormbaena.spring6restmvc.services;

import com.victormbaena.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("Customer one Cat")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .version(2)
                .customerName("Customer two Cat")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .version(3)
                .customerName("Customer three Cat")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }
    @Override
    public List<Customer> listCustomer() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID customerId) {
        return customerMap.get(customerId);
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {
        Customer customerSaved = Customer.builder()
                .customerName(customer.getCustomerName())
                .version(customer.getVersion())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .id(UUID.randomUUID())
                .build();
        customerMap.put(customerSaved.getId(), customerSaved);

        return customerSaved;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {
        Customer exits = customerMap.get(customerId);
        exits.setCustomerName(customer.getCustomerName());
        exits.setVersion(customer.getVersion());
        exits.setLastModifiedDate(LocalDateTime.now());
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, Customer customer) {
        Customer exists = customerMap.get(customerId);
        if (StringUtils.hasText(customer.getCustomerName())) {
            exists.setCustomerName(customer.getCustomerName());
        }
        if (customer.getVersion() != null) {
            exists.setVersion(customer.getVersion());
        }
        exists.setLastModifiedDate(LocalDateTime.now());
    }
}
