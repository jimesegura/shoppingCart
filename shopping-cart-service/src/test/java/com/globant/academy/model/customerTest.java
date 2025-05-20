package com.globant.academy.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class customerTest {

    @Test
    public void givenACustomer_throwsExceptionWhenIdIsNull() {
        String name="John";
        String lastname="Smith";
        LocalDate birthdate= LocalDate.of(1989,12,13);
        String email ="unemail@gmail.com";
        Integer phone= 123456789;
        Integer idNumber= 1234;
        assertThrows(IllegalArgumentException.class, () -> new Customer(null, name, lastname, birthdate, email, phone, idNumber));
    }

    @Test
    public void givenACustomer_throwsExceptionWhenNameIsNull() {
        Integer id=1;
        String lastname="Smith";
        LocalDate birthdate= LocalDate.of(1989,12,13);
        String email ="unemail@gmail.com";
        Integer phone= 123456789;
        Integer idNumber= 1234;
        assertThrows(IllegalArgumentException.class, () -> new Customer(id, null, lastname, birthdate, email, phone, idNumber));
    }

    @Test
    public void givenACustomer_throwsExceptionWhenLastnameIsNull() {
        Integer id=1;
        String name="John";
        LocalDate birthdate= LocalDate.of(1989,12,13);
        String email ="unemail@gmail.com";
        Integer phone= 123456789;
        Integer idNumber= 1234;
        assertThrows(IllegalArgumentException.class, () -> new Customer(id, name, null, birthdate, email, phone, idNumber));
    }

    @Test
    public void givenACustomer_throwsExceptionWhenBirthdateIsNull() {
        Integer id=1;
        String name="John";
        String lastName="Smith";
        String email ="unemail@gmail.com";
        Integer phone= 123456789;
        Integer idNumber= 1234;
        assertThrows(IllegalArgumentException.class, () -> new Customer(id, name, lastName, null, email, phone, idNumber));
    }

    @Test
    public void givenACustomer_throwsExceptionWheEmailIsNull() {
        Integer id=1;
        String name="John";
        String lastName="Smith";
        LocalDate birthdate= LocalDate.of(1989,12,13);
        Integer phone= 123456789;
        Integer idNumber= 1234;
        assertThrows(IllegalArgumentException.class, () -> new Customer(id, name, lastName, birthdate, null, phone, idNumber));
    }

    @Test
    public void givenACustomer_throwsExceptionWhePhoneIsNull() {
        Integer id=1;
        String name="John";
        String lastName="Smith";
        LocalDate birthdate= LocalDate.of(1989,12,13);
        String email= "unemail@gmail.com";
        Integer idNumber= 1234;
        assertThrows(IllegalArgumentException.class, () -> new Customer(id, name, lastName, birthdate, email, null, idNumber));
    }

    @Test
    public void givenACustomer_throwsExceptionWhenIdNumberIsNull() {
        Integer id=1;
        String name="John";
        String lastName="Smith";
        LocalDate birthdate= LocalDate.of(1989,12,13);
        String email= "unemail@gmail.com";
        Integer phone= 123456789;
        assertThrows(IllegalArgumentException.class, () -> new Customer(id, name, lastName, birthdate, email, phone, null));
    }
}
