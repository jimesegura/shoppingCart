package com.globant.academy.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.academy.ShoppingCartApp;
import com.globant.academy.dto.ProductDTO;
import com.globant.academy.exception.ProductNotFoundException;
import com.globant.academy.mapper.ProductMapper;
import com.globant.academy.model.Product;
import com.globant.academy.model.ProductType;
import com.globant.academy.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private ProductDTO productDTO;


    @BeforeEach
    public void setUp(){
        product= new Product(1, ProductType.ELECTRONIC, BigDecimal.valueOf(200),"TV","description");
        productDTO = ProductDTO.builder()
                .id(product.getId())
                .type(product.getType())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription()).build();

        when(mapper.toDto(product)).thenReturn(productDTO);
    }

    @Test
    public void givenAProduct_whenCreateProduct_thenProductIsCreated() throws Exception {
        Mockito.when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));

        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }


    @Test
    public void givenProducts_whenGetAllProducts_thenReturnAllProducts() throws Exception {
        Mockito.when(productService.findAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(product.getId()))
                .andExpect(jsonPath("$[0].name").value(product.getName()));
    }

    @Test
    public void givenAnUpdatedProduct_whenUpdateProduct_thenProductIsUpdated() throws Exception, ProductNotFoundException {
        Mockito.when(productService.updateProduct(any(Integer.class),any(ProductDTO.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    public void givenAProduct_whenDeleteProduct_thenProductIsDeleted() throws Exception, ProductNotFoundException {
        doNothing().when(productService).deleteProduct(product.getId());

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(1);
    }

    @Test
    public void givenId_whenGetById_thenReturnProduct() throws Exception, ProductNotFoundException {
        Integer id=1;
        Mockito.when(productService.findById(id)).thenReturn(product);

        mockMvc.perform(get("/api/products/1")
                        .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

}
