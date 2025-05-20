package com.globant.academy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "product_Type")
    @Enumerated(EnumType.STRING)
    private ProductType type;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItemList;


    public Product(Integer id,ProductType type, BigDecimal price, String name, String description) {
        validateProduct(id, type, price, name, description);
        this.id = id;
        this.type = type;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    private void validateProduct(Integer id, ProductType type, BigDecimal price, String name, String description){
        if(Objects.isNull(id))throw new IllegalArgumentException("ID cannot be null");
        if(Objects.isNull(type))throw new IllegalArgumentException("Type cannot be null");
        if(Objects.isNull(price))throw  new IllegalArgumentException("Price cannot be null");
        if(Objects.isNull(name))throw new IllegalArgumentException("Name cannot be null");
        if(Objects.isNull(description))throw new IllegalArgumentException("Description cannot be null");
    }

}
