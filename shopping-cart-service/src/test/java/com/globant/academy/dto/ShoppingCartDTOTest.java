package com.globant.academy.dto;

import com.globant.academy.model.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;

public class ShoppingCartDTOTest {

        @Test
        void givenShoppingCart_whenIdIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new ShoppingCartDTO(null, Arrays.asList(), Status.DRAFT);
            });
            assertEquals("Customer cannot be null", exception.getMessage());
        }

        @Test
        void givenShoppingCart_whenAllParameterAreValid_thenShoppingCartIsCreated() {
            CustomerDTO customerDTO = new CustomerDTO(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                    "john.doe@example.com", 123456789, 12345678);
            Status status = Status.DRAFT;


            ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO(customerDTO, Arrays.asList(), status);

            assertNotNull(shoppingCartDTO);
            assertEquals(customerDTO, shoppingCartDTO.getCustomer());
            assertEquals(status, shoppingCartDTO.getStatus());
            assertTrue(shoppingCartDTO.getProducts().isEmpty());
        }



}
