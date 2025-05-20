package com.globant.academy.mapper;

import com.globant.academy.dto.ProductDTO;
import com.globant.academy.model.Product;
import com.globant.academy.model.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class ProductMapperTest {

        @InjectMocks
        private ProductMapper productMapper;

        private Product product;
        private ProductDTO productDTO;

        @BeforeEach
        void setUp() {

            MockitoAnnotations.openMocks(this);


            product = new Product(1, ProductType.ELECTRONIC, BigDecimal.valueOf(100),"product a", "Description");
            productDTO = new ProductDTO(1, ProductType.ELECTRONIC,BigDecimal.valueOf(200),"product B","description");
        }

        @Test
        void givenProduct_whenToDto_thenProductDTOMappedCorrectly() {

            ProductDTO dto = productMapper.toDto(product);


            assertEquals(product.getId(), dto.getId());
            assertEquals(product.getName(), dto.getName());
            assertEquals(product.getType(), dto.getType());
            assertEquals(product.getPrice(), dto.getPrice());
            assertEquals(product.getDescription(), dto.getDescription());
        }

        @Test
        void givenProductDTO_whenToEntity_thenProductMappedCorrectly() {

            Product entity = productMapper.toEntity(productDTO);


            assertEquals(productDTO.getId(), entity.getId());
            assertEquals(productDTO.getName(), entity.getName());
            assertEquals(productDTO.getType(), entity.getType());
            assertEquals(productDTO.getPrice(), entity.getPrice());
            assertEquals(productDTO.getDescription(), entity.getDescription());
        }


}
