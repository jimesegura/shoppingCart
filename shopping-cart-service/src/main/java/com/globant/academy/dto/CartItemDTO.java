package com.globant.academy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Integer id;
    private ProductDTO product;
    private Integer quantity;

    public CartItemDTO(ProductDTO product, Integer quantity) {
        validateCartItem(product,quantity);
        this.product= product;
        this.quantity = quantity;
    }

    private void validateCartItem(ProductDTO product, Integer quantity){
        if(Objects.isNull(product))throw new IllegalArgumentException("Product cannot be null");
        if(Objects.isNull(quantity))throw new IllegalArgumentException("Quantity cannot be null");
        if(quantity<=0)throw new IllegalArgumentException("Quantity must be greater than 0");
    }
}
