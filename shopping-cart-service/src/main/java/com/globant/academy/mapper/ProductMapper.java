package com.globant.academy.mapper;

import com.globant.academy.dto.ProductDTO;
import com.globant.academy.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements ModelMapper<Product, ProductDTO> {


    @Override
    public ProductDTO toDto(Product entity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(entity.getId());
        productDTO.setName(entity.getName());
        productDTO.setType(entity.getType());
        productDTO.setPrice(entity.getPrice());
        productDTO.setDescription(entity.getDescription());
        return productDTO;
    }

    @Override
    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setType(dto.getType());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        return product;
    }
}
