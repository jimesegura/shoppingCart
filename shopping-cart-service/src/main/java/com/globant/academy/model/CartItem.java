package com.globant.academy.model;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cartitem")
public class CartItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;


    public CartItem(Integer id, Product product, Integer quantity) {
        validateCartItem(id, product, quantity);
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }


    private void validateCartItem(Integer id, Product product, Integer quantity){
        if(Objects.isNull(id))throw new IllegalArgumentException("Id cannot be null");
        if(Objects.isNull(product))throw new IllegalArgumentException("Product cannot be null");
        if(Objects.isNull(quantity))throw new IllegalArgumentException("Quantity cannot be null");
        if(quantity<=0)throw new IllegalArgumentException("Quantity must be greater than 0");
    }

    public BigDecimal getPrice(){
        BigDecimal quantityBD= new BigDecimal(this.getQuantity());
        return this.getProduct().getPrice().multiply(quantityBD);
    }
}
