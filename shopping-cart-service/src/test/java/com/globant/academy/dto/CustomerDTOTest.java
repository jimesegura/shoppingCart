package com.globant.academy.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class CustomerDTOTest {



        @Test
        void givenCustomer_whenNameIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new CustomerDTO(null, "Doe", LocalDate.of(2000, 1, 1), "john.doe@example.com", 1234567890, 123456);
            });
            assertEquals("Name cannot be null", exception.getMessage());
        }

        @Test
        void givenCustomer_whenLastnameIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new CustomerDTO("John", null, LocalDate.of(2000, 1, 1), "john.doe@example.com", 1234567890, 123456);
            });
            assertEquals("Lastname cannot be null", exception.getMessage());
        }

        @Test
        void givenCustomer_whenBirthdateIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new CustomerDTO("John", "Doe", null, "john.doe@example.com", 1234567890, 123456);
            });
            assertEquals("Birthdate cannot be null", exception.getMessage());
        }

        @Test
        void givenCustomer_whenEmailIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new CustomerDTO("John", "Doe", LocalDate.of(2000, 1, 1), null, 1234567890, 123456);
            });
            assertEquals("Email cannot be null", exception.getMessage());
        }

        @Test
        void givenCustomer_whenPhoneIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new CustomerDTO("John", "Doe", LocalDate.of(2000, 1, 1), "john.doe@example.com", null, 123456);
            });
            assertEquals("Phone cannot be null", exception.getMessage());
        }

        @Test
        void givenCustomer_whenIdNumberIsNull_thenThrowsException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new CustomerDTO("John", "Doe", LocalDate.of(2000, 1, 1), "john.doe@example.com", 1234567890, null);
            });
            assertEquals("IDNumber cannot be null", exception.getMessage());
        }

}
