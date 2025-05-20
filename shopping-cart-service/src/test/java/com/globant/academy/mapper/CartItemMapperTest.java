package com.globant.academy.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.globant.academy.dto.CartItemDTO;
import com.globant.academy.dto.ProductDTO;
import com.globant.academy.model.CartItem;
import com.globant.academy.model.Product;
import com.globant.academy.model.ProductType;


public class CartItemMapperTest {

        private CartItemMapper cartItemMapper;

        @Mock
        private ProductMapper productMapper;

        private Product product;
        private CartItem cartItem;
        private CartItemDTO cartItemDTO;
        private ProductDTO productDto;

        @BeforeEach
        void setUp() {
            productMapper = mock(ProductMapper.class);

            cartItemMapper = new CartItemMapper(productMapper);

            product = new Product(1, ProductType.ELECTRONIC, BigDecimal.valueOf(100),"product a", "Description");
            productDto = new ProductDTO(1, ProductType.ELECTRONIC, BigDecimal.valueOf(100),"product a", "Description");

            cartItem = new CartItem(1, product, 2);
            cartItemDTO = new CartItemDTO(1,productDto, 2);

        }

        @Test
        void givenCartItem_whenToDTO_thenCartItemDTOMappedCorrectly() {

            when(productMapper.toDto(cartItem.getProduct())).thenReturn(cartItemDTO.getProduct());

            CartItemDTO cartItemDTO = cartItemMapper.toDto(cartItem);

            assertEquals(cartItem.getQuantity(), cartItemDTO.getQuantity());
            assertEquals(cartItem.getProduct().getId(), cartItemDTO.getProduct().getId());
            assertEquals(cartItem.getProduct().getPrice(), cartItemDTO.getProduct().getPrice());
            assertEquals(cartItem.getProduct().getName(), cartItemDTO.getProduct().getName());
        }

        @Test
        void givenCartItemDTO_whenToEntity_thenCartItemEntityMappedCorrectly() {
               when(productMapper.toEntity(cartItemDTO.getProduct())).thenReturn(product);

            CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);

            assertEquals(cartItemDTO.getQuantity(), cartItem.getQuantity());
            assertEquals(cartItemDTO.getProduct().getId(), cartItem.getProduct().getId());
            assertEquals(cartItemDTO.getProduct().getPrice(), cartItem.getProduct().getPrice());
            assertEquals(cartItemDTO.getProduct().getName(), cartItem.getProduct().getName());
        }
}
