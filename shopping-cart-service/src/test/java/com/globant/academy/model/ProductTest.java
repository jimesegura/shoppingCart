package com.globant.academy.model;

import com.globant.academy.service.ProductService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void givenAProduct_throwsExceptionWhenIdIsNull() {
        ProductType type= ProductType.ELECTRONIC;
        BigDecimal price= new BigDecimal("200");
        String name= "TV";
        String description="A description";
        assertThrows(IllegalArgumentException.class, () -> new Product(null, type, price, name, description));
    }

    @Test
    public void givenAProduct_throwsExceptionWhenTypeIsNull() {
        Integer id= 1;
        BigDecimal price= new BigDecimal("200");
        String name= "TV";
        String description="A description";
        assertThrows(IllegalArgumentException.class, () -> new Product(id, null, price, name, description));
    }

    @Test
    public void givenAProduct_throwsExceptionWhenPriceIsNull() {
        Integer id=1;
        ProductType type= ProductType.ELECTRONIC;
        String name= "TV";
        String description="A description";
        assertThrows(IllegalArgumentException.class, () -> new Product(id, type, null, name, description));
    }

    @Test
    public void givenAProduct_throwsExceptionWhenNameeIsNull() {
        Integer id=1;
        ProductType type= ProductType.ELECTRONIC;
        BigDecimal price= new BigDecimal("200");
        String description="A description";
        assertThrows(IllegalArgumentException.class, () -> new Product(id, type, price, null, description));
    }

    @Test
    public void givenAProduct_throwsExceptionWhenDescriptionIsNull() {
        Integer id=1;
        ProductType type= ProductType.ELECTRONIC;
        BigDecimal price= new BigDecimal("200");
        String name= "TV";
        assertThrows(IllegalArgumentException.class, () -> new Product(id, type, price, name, null));
    }
}
