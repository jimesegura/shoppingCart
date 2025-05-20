package com.globant.academy.mapper;

import com.globant.academy.dto.CustomerDTO;
import com.globant.academy.model.Customer;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper implements ModelMapper<Customer, CustomerDTO> {

    @Override
    public CustomerDTO toDto(Customer entity) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(entity.getId());
        customerDTO.setName(entity.getName());
        customerDTO.setLastname(entity.getLastname());
        customerDTO.setBirthdate(entity.getBirthdate());
        customerDTO.setEmail(entity.getEmail());
        customerDTO.setPhone(entity.getPhone());
        customerDTO.setIdNumber(entity.getIdNumber());
        return customerDTO;
    }

    @Override
    public Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setLastname(dto.getLastname());
        customer.setBirthdate(dto.getBirthdate());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setIdNumber(dto.getIdNumber());
        return customer;
    }
}