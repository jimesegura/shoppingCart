package com.globant.academy.mapper;

import com.globant.academy.dto.ShoppingCartDTO;
import com.globant.academy.model.ShoppingCart;
import org.springframework.stereotype.Component;


@Component
public class ShoppingCartMapper  implements  ModelMapper<ShoppingCart, ShoppingCartDTO>{
    private final CustomerMapper customerMapper;
    private final CartItemMapper cartItemMapper;

    public ShoppingCartMapper(CustomerMapper customerMapper, CartItemMapper cartItemMapper) {
        this.customerMapper = customerMapper;
        this.cartItemMapper = cartItemMapper;
    }


    @Override
    public ShoppingCartDTO toDto(ShoppingCart entity) {
        ShoppingCartDTO shoppingCartDTO= new ShoppingCartDTO();
        shoppingCartDTO.setId(entity.getId());
        shoppingCartDTO.setStatus(entity.getStatus());
        shoppingCartDTO.setCustomer(customerMapper.toDto(entity.getCustomer()));
        shoppingCartDTO.setProducts(cartItemMapper.toDTOList(entity.getProductList()));
        return shoppingCartDTO;
    }

    @Override
    public ShoppingCart toEntity(ShoppingCartDTO dto) {
        ShoppingCart shoppingCart= new ShoppingCart();
        shoppingCart.setId(dto.getId());
        shoppingCart.setStatus(dto.getStatus());
        shoppingCart.setCustomer(customerMapper.toEntity(dto.getCustomer()));
        shoppingCart.setProductList(cartItemMapper.toEntityList(dto.getProducts()));
        return shoppingCart;
    }
}
