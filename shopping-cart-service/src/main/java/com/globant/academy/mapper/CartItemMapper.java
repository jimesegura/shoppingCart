package com.globant.academy.mapper;

import com.globant.academy.dto.CartItemDTO;
import com.globant.academy.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper implements ModelMapper<CartItem, CartItemDTO> {
    private final ProductMapper productMapper;


    public CartItemMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public CartItemDTO toDto(CartItem entity) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(entity.getId());
        dto.setProduct(productMapper.toDto(entity.getProduct()));
        dto.setQuantity(entity.getQuantity());

        return dto;
    }

    @Override
    public CartItem toEntity(CartItemDTO dto) {
        CartItem entity = new CartItem();
        entity.setId(dto.getId());
        entity.setProduct(productMapper.toEntity(dto.getProduct()));
        entity.setQuantity(dto.getQuantity());

        return entity;
    }
}
