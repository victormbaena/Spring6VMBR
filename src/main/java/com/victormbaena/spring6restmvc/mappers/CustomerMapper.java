package com.victormbaena.spring6restmvc.mappers;

import com.victormbaena.spring6restmvc.entities.Customer;
import com.victormbaena.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
