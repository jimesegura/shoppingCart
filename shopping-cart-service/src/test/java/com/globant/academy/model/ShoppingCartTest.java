package com.globant.academy.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    private Customer customer;

    @Test
    public void givenAShoppingCart_ThrowsExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new ShoppingCart(null, customer, new ArrayList<>()));
    }

    @Test
    public void givenAShoppingCart_ThrowsExceptionWhenCustomerIsNull() {
        Integer id=1;
        assertThrows(IllegalArgumentException.class, () -> new ShoppingCart(id, null, new ArrayList<>()));
    }
}
