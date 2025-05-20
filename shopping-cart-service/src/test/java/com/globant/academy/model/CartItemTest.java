package com.globant.academy.model;

import com.globant.academy.service.CartItemService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartItemTest {

    @Test
    public void givenACartItem_throwsExceptionWhenIdIsNull() {
        Product product = new Product(1, ProductType.ELECTRONIC, BigDecimal.valueOf(200), "TV", "description");
        Integer quantity= 1;
        assertThrows(IllegalArgumentException.class, () -> new CartItem(null, product, quantity));
    }

    @Test
    public void givenACartItem_throwsExceptionWhenProductIsNull() {
        Integer id=1;
        Integer quantity= 1;
        assertThrows(IllegalArgumentException.class, () -> new CartItem(id, null, quantity));
    }

    @Test
    public void givenACartItem_throwsExceptionWhenQuantityIsNull() {
        Integer id=1;
        Product product = new Product(1, ProductType.ELECTRONIC, BigDecimal.valueOf(200), "TV", "description");
        assertThrows(IllegalArgumentException.class, () -> new CartItem(id, product, null));
    }

    @Test
    public void givenACartItem_throwsExceptionWhenQuantityIs0orBelow0() {
        Integer id=1;
        Product product = new Product(1, ProductType.ELECTRONIC, BigDecimal.valueOf(200), "TV", "description");
        Integer quantity= 0;
        assertThrows(IllegalArgumentException.class, () -> new CartItem(id, product, quantity));
    }

    @Test
    public void givenACartItem_testGetPrice(){
        Integer id=1;
        Product product = new Product(1, ProductType.ELECTRONIC, BigDecimal.valueOf(200), "TV", "description");
        Integer quantity= 2;
        CartItem cartItem= new CartItem(id,product,quantity);

        BigDecimal quantityBD= new BigDecimal(quantity);
        BigDecimal expectedResult=product.getPrice().multiply(quantityBD);

        assertEquals(expectedResult,cartItem.getPrice());
    }
}
