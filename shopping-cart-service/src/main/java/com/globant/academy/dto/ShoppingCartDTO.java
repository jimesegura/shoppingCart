package com.globant.academy.dto;
import com.globant.academy.exception.ShoppingCartNotFoundException;
import com.globant.academy.model.ShoppingCart;
import com.globant.academy.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ShoppingCartDTO {

    private Integer id;
    private CustomerDTO customer;
    private List<CartItemDTO> products;
    private Status status;
    private BigDecimal totalPrice;

    public ShoppingCartDTO(CustomerDTO customer, List<CartItemDTO> productList, Status status) {
        validateCart(customer);
        this.customer = customer;
        this.products = productList;
        this.status = status;
    }

    private void validateCart(CustomerDTO customer){
        if(Objects.isNull(customer))throw new IllegalArgumentException("Customer cannot be null");
    }

    public BigDecimal getTotalPrice() throws ShoppingCartNotFoundException {
        return this.getProducts().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
