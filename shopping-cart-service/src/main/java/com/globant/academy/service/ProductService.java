package com.globant.academy.service;

import com.globant.academy.dto.ProductDTO;
import com.globant.academy.exception.ProductNotFoundException;
import com.globant.academy.mapper.ProductMapper;
import com.globant.academy.model.Product;
import com.globant.academy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    public final ProductRepository productRepository;

    public final ProductMapper mappers;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper mappers) {
        this.productRepository = productRepository;
        this.mappers = mappers;
    }

    public Product createProduct(ProductDTO productDTO){
            Product product = mappers.toEntity(productDTO);
            return productRepository.save(product);
    }

    public List<Product> findAll(){
       return productRepository.findAll();

    }

    @CacheEvict(value = "productsCache", key = "#id")
    public Product updateProduct(Integer id,ProductDTO productDTO) throws ProductNotFoundException {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Could not find product with id: "+id));

        findProduct.setType(productDTO.getType());
        findProduct.setName(productDTO.getName());
        findProduct.setPrice(productDTO.getPrice());
        findProduct.setDescription(productDTO.getDescription());

        return  productRepository.save(findProduct);
    }

    public void deleteProduct(Integer id) throws ProductNotFoundException {
        Optional<Product> findProduct = productRepository.findById(id);
        if(findProduct.isPresent()) {
            productRepository.delete(findProduct.get());
        }else {
            throw new ProductNotFoundException("Could not delete product with id: "+id);
        }
    }

    @Cacheable(value = "productsCacheByCart", key = "#cartId")
    public List<Product> getProductsByCartId(Integer cartId){
           return productRepository.getProductsByCartId(cartId);
    }

    @Cacheable(value = "productsCache", key = "#id")
    public Product findById(Integer id) throws ProductNotFoundException {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Could not find product"));
        return findProduct;
    }
}
