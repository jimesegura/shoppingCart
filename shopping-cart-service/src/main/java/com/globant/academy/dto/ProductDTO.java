package com.globant.academy.dto;
import com.globant.academy.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer id;
    private ProductType type;
    private BigDecimal price;
    private String name;
    private String description;

    public ProductDTO(ProductType type, BigDecimal price, String name, String description) {
        validateProduct(type,price,name,description);
        this.type = type;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    private void validateProduct(ProductType type, BigDecimal price, String name, String description){
        if(Objects.isNull(type))throw new IllegalArgumentException("Type cannot be null");
        if(Objects.isNull(price))throw  new IllegalArgumentException("Price cannot be null");
        if(Objects.isNull(name))throw new IllegalArgumentException("Name cannot be null");
        if(Objects.isNull(description))throw new IllegalArgumentException("Description cannot be null");
    }
}
