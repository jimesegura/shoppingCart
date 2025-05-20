package com.globant.academy.service;

import com.globant.academy.config.RabbitMQConfig;
import com.globant.academy.dto.CustomerDTO;
import com.globant.academy.dto.Message;
import com.globant.academy.dto.NotificationDTO;
import com.globant.academy.exception.CustomerNotFoundException;
import com.globant.academy.mapper.CustomerMapper;
import com.globant.academy.model.Customer;
import com.globant.academy.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private RabbitMQConfig rabbitMQConfig;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private CustomerService customerService;

    private CustomerDTO customerDTO;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customerDTO = new CustomerDTO();
        customerDTO.setName("John");
        customerDTO.setLastname("Doe");
        customerDTO.setBirthdate(LocalDate.of(1990, 5, 15));
        customerDTO.setEmail("john.doe@example.com");
        customerDTO.setPhone(123456789);
        customerDTO.setIdNumber(12345);

        customer = new Customer();
        customer.setId(1);
        customer.setName("John");
        customer.setLastname("Doe");
        customer.setBirthdate(LocalDate.of(1990, 5, 15));
        customer.setEmail("john.doe@example.com");
        customer.setPhone(123456789);
        customer.setIdNumber(12345);
    }

    @Test
    public void givenCustomer_whenCreateCustomer_thenCustomerIsCreated() {
        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customerDTO);

        assertNotNull(createdCustomer);
        assertEquals(customerDTO.getName(), createdCustomer.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void givenCustomersList_whenFindAll_thenReturnAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> foundCustomers = customerService.findAll();

        assertNotNull(foundCustomers);
        assertEquals(1, foundCustomers.size());
        assertEquals("John", foundCustomers.get(0).getName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void givenCustomer_whenUpdateCustomer_thenCustomerIsUpdated() throws CustomerNotFoundException {
        CustomerDTO updateDTO = new CustomerDTO();
        updateDTO.setName("John Updated");
        updateDTO.setLastname("Doe Updated");
        updateDTO.setBirthdate(LocalDate.of(1991, 6, 20));
        updateDTO.setEmail("john.updated@example.com");
        updateDTO.setPhone(987654321);
        updateDTO.setIdNumber(54321);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = customerService.updateCustomer(1, updateDTO);

        assertNotNull(updatedCustomer);
        assertEquals("John Updated", updatedCustomer.getName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void givenCustomer_whenUpdateCustomerNotFound_thenReturnCustomerNotFoundException() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(1, customerDTO));
    }

    @Test
    public void givenCustomer_whenDeleteCustomer_thenCustomerIsDeleted() throws CustomerNotFoundException {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1);

        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void givenCustomer_whenDeleteCustomerNotFound_thenThrowsCustomerNotFoundException() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1));
    }
}























