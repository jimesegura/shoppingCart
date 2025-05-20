package com.globant.academy.mapper;

import com.globant.academy.dto.CustomerDTO;
import com.globant.academy.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class CustomerMapperTest {



        @InjectMocks
        private CustomerMapper customerMapper;

        private Customer customer;
        private CustomerDTO customerDTO;

        @BeforeEach
        void setUp() {

            MockitoAnnotations.openMocks(this);


            customer = new Customer(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                    "john.doe@example.com", 123456789, 12345678);
            customerDTO = new CustomerDTO(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                    "john.doe@example.com", 123456789, 12345678);
        }

        @Test
        void givenCustomer_whenToDto_thenCustomerDTOMappedCorrectly() {

            CustomerDTO dto = customerMapper.toDto(customer);


            assertEquals(customer.getId(), dto.getId());
            assertEquals(customer.getName(), dto.getName());
            assertEquals(customer.getLastname(), dto.getLastname());
            assertEquals(customer.getBirthdate(), dto.getBirthdate());
            assertEquals(customer.getEmail(), dto.getEmail());
            assertEquals(customer.getPhone(), dto.getPhone());
            assertEquals(customer.getIdNumber(), dto.getIdNumber());
        }

        @Test
        void givenCustomerDTO_whenToEntity_thenCustomerMappedCorrectly() {

            Customer entity = customerMapper.toEntity(customerDTO);


            assertEquals(customerDTO.getId(), entity.getId());
            assertEquals(customerDTO.getName(), entity.getName());
            assertEquals(customerDTO.getLastname(), entity.getLastname());
            assertEquals(customerDTO.getBirthdate(), entity.getBirthdate());
            assertEquals(customerDTO.getEmail(), entity.getEmail());
            assertEquals(customerDTO.getPhone(), entity.getPhone());
            assertEquals(customerDTO.getIdNumber(), entity.getIdNumber());
        }

}
