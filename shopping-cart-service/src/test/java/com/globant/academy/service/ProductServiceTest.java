package com.globant.academy.service;

import com.globant.academy.dto.ProductDTO;
import com.globant.academy.exception.CustomerNotFoundException;
import com.globant.academy.exception.ProductNotFoundException;
import com.globant.academy.mapper.ProductMapper;
import com.globant.academy.model.Product;
import com.globant.academy.model.ProductType;
import com.globant.academy.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        productDTO = new ProductDTO();
        productDTO.setType(ProductType.ELECTRONIC);
        productDTO.setName("Smartphone");
        productDTO.setPrice(new BigDecimal("699.99"));
        productDTO.setDescription("Latest model with high-end features");

        product = new Product();
        product.setId(1);
        product.setType(ProductType.ELECTRONIC);
        product.setName("Smartphone");
        product.setPrice(new BigDecimal("699.99"));
        product.setDescription("Latest model with high-end features");
    }

    @Test
    public void givenProduct_whenCreateProduct_thenProductIsCreated() {
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(productDTO);

        assertNotNull(createdProduct);
        assertEquals(productDTO.getName(), createdProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void givenProductList_whenFindAll_thenReturnAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> foundProducts = productService.findAll();

        assertNotNull(foundProducts);
        assertEquals(1, foundProducts.size());
        assertEquals("Smartphone", foundProducts.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void givenProduct_whenUpdateProduct_thenProductIsUpdated() throws ProductNotFoundException {
        ProductDTO updateDTO = new ProductDTO();
        updateDTO.setType(ProductType.ELECTRONIC);
        updateDTO.setName("Smartphone Pro");
        updateDTO.setPrice(new BigDecimal("799.99"));
        updateDTO.setDescription("Upgraded version with more features");

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateProduct(1, updateDTO);

        assertNotNull(updatedProduct);
        assertEquals("Smartphone Pro", updatedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void givenProduct_whenUpdateProductNotFound_thenThrowsProductNotFoundException() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1, productDTO));
    }

    @Test
    public void givenProduct_whenDeleteProduct_thenProductIsDeleted() throws ProductNotFoundException {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        productService.deleteProduct(1);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void givenProduct_whenDeleteProductNotFound_thenTowsProductNotFoundException() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1));
    }

    @Test
    public void givenCartId_whenGetProductsByCartId_thenReturnAllProductsFromThatCart() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.getProductsByCartId(1)).thenReturn(products);

        List<Product> foundProducts = productService.getProductsByCartId(1);

        assertNotNull(foundProducts);
        assertEquals(1, foundProducts.size());
        assertEquals("Smartphone", foundProducts.get(0).getName());
        verify(productRepository, times(1)).getProductsByCartId(1);
    }

    @Test
    public void givenId_whenFindById_thenReturnProduct() throws ProductNotFoundException {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product foundProduct = productService.findById(1);

        assertNotNull(foundProduct);
        assertEquals("Smartphone", foundProduct.getName());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    public void givenWrongId_whenFindById_thenProductNotFound() throws ProductNotFoundException {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById(1));
        verify(productRepository, times(1)).findById(1);
    }
}

