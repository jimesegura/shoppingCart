package com.globant.academy.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartItemDTOTest {

        @Test
        void givenCartItem_whenValidParameters_thenCartItemIsCreated() {

            ProductDTO product = mock(ProductDTO.class);
            Integer quantity = 5;


            CartItemDTO cartItemDTO = new CartItemDTO(product, quantity);


            assertNotNull(cartItemDTO);
            assertEquals(product, cartItemDTO.getProduct());
            assertEquals(quantity, cartItemDTO.getQuantity());
        }

        @Test
        void givenCartItem_whenProductIsNull_thenThrowsException() {

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new CartItemDTO(null, 5);
            });
            assertEquals("Product cannot be null", exception.getMessage());
        }

        @Test
        void givenCartItem_whenQuantityIsNull_thenThrowsException() {

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new CartItemDTO(mock(ProductDTO.class), null);
            });
            assertEquals("Quantity cannot be null", exception.getMessage());
        }

        @Test
        void givenCartItem_whenQuantityEquals0OrIsBellow0_thenThrowsException() {

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new CartItemDTO(mock(ProductDTO.class), 0);
            });
            assertEquals("Quantity must be greater than 0", exception.getMessage());

            exception = assertThrows(IllegalArgumentException.class, () -> {
                new CartItemDTO(mock(ProductDTO.class), -1);
            });
            assertEquals("Quantity must be greater than 0", exception.getMessage());
        }
}
