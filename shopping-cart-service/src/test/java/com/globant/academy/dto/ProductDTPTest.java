package com.globant.academy.dto;

import com.globant.academy.model.ProductType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class ProductDTPTest {

        @Test
        void givenProduct_whenTypeIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new ProductDTO(null, BigDecimal.valueOf(10.99), "Product Name", "Product Description");
            });
            assertEquals("Type cannot be null", exception.getMessage());
        }

        @Test
        void givenProduct_whenPriceIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new ProductDTO(ProductType.ELECTRONIC, null, "Product Name", "Product Description");
            });
            assertEquals("Price cannot be null", exception.getMessage());
        }

        @Test
        void givenProduct_whenNameIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new ProductDTO(ProductType.ELECTRONIC, BigDecimal.valueOf(10.99), null, "Product Description");
            });
            assertEquals("Name cannot be null", exception.getMessage());
        }

        @Test
        void givenProduct_whenDescriptionIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new ProductDTO(ProductType.ELECTRONIC, BigDecimal.valueOf(10.99), "Product Name", null);
            });
            assertEquals("Description cannot be null", exception.getMessage());
        }

}
