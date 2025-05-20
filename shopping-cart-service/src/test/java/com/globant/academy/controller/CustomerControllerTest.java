package com.globant.academy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.academy.ShoppingCartApp;
import com.globant.academy.dto.CustomerDTO;
import com.globant.academy.exception.CustomerNotFoundException;
import com.globant.academy.mapper.CustomerMapper;
import com.globant.academy.model.Customer;
import com.globant.academy.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerMapper mapper; // Mocking CustomerMapper

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;
    private CustomerDTO customerDTO;


    @BeforeEach
    public void setUp() {
        customer = new Customer(1,"Juan", "Perez", LocalDate.of(1989, 12, 13), "juan@email.com", 123456789, 1234);
        customerDTO = mapper.toDto(customer);
        when(mapper.toDto(customer)).thenReturn(customerDTO);
    }

    @Test
    public void givenACustomer_whenCreateCustomer_thenCustomerIsCreated() throws Exception {
        Mockito.when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customer);


        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());

        verify(customerService, times(1)).createCustomer(any(CustomerDTO.class));
    }

    @Test
    public void givenCustomers_whenGetAllCustomer_thenReturnAllCustomers() throws Exception {
        Mockito.when(customerService.findAll()).thenReturn(List.of(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUpdatedCustomer_whenUpdateCustomer_thenCustomerIsUpdated() throws Exception, CustomerNotFoundException {
        when(customerService.updateCustomer(any(Integer.class),any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
        verify(customerService, times(1)).updateCustomer(any(Integer.class),any(CustomerDTO.class));
    }

    @Test
    public void givenACustomer_whenDeleteCustomer_thenCustomerIsDeleted() throws Exception, CustomerNotFoundException {
        doNothing().when(customerService).deleteCustomer(1);

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomer(1);
    }
}