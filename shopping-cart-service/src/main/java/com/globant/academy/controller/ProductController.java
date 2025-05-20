package com.globant.academy.controller;

import com.globant.academy.dto.ProductDTO;
import com.globant.academy.exception.ProductNotFoundException;
import com.globant.academy.mapper.ProductMapper;
import com.globant.academy.model.Product;
import com.globant.academy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper mappers;

    @Autowired
    public ProductController(ProductService productService, ProductMapper mappers) {
        this.productService = productService;
        this.mappers = mappers;
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product createProduct = productService.createProduct(productDTO);
        ProductDTO finalProduct = mappers.toDto(createProduct);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(finalProduct);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<Product>productList = productService.findAll();
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> mappers.toDto(product))
                .toList();
        return productDTOS.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id,@RequestBody ProductDTO productDTO) throws ProductNotFoundException {
        Product product = productService.updateProduct(id,productDTO);
        return ResponseEntity.ok(mappers.toDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) throws ProductNotFoundException {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) throws ProductNotFoundException {
        Product product = productService.findById(id);
        ProductDTO productDTO = mappers.toDto(product);
        return ResponseEntity.ok(productDTO);
    }

}
