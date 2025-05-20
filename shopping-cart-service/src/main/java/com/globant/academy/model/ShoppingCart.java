package com.globant.academy.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "shoppingcart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> productList;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;


    public ShoppingCart(Integer id,Customer customer, List<CartItem>productList) {
        validateCart(id, customer);
        this.id = id;
        this.customer = customer;
        this.productList = productList!=null ? productList : new ArrayList<>();
        this.status = Status.DRAFT;
    }

    private void validateCart(Integer id, Customer customer){
        if(Objects.isNull(id))throw new IllegalArgumentException("ID cannot be null");
        if(Objects.isNull(customer))throw new IllegalArgumentException("Customer cannot be null");
    }
}
