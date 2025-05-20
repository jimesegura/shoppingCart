package com.globant.academy.service;

import com.globant.academy.dto.CartItemDTO;
import com.globant.academy.exception.CartItemNotFoundException;
import com.globant.academy.exception.ProductNotFoundException;
import com.globant.academy.exception.ShoppingCartNotFoundException;
import com.globant.academy.mapper.ProductMapper;
import com.globant.academy.model.CartItem;
import com.globant.academy.model.Product;
import com.globant.academy.model.ShoppingCart;
import com.globant.academy.repository.CartItemRepository;
import com.globant.academy.repository.ProductRepository;
import com.globant.academy.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }




    public CartItem createCartItem(Integer cartId, Integer productId, Integer quantity) throws ShoppingCartNotFoundException, ProductNotFoundException {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("ShoppingCart with ID " + cartId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);
        cartItem.setProduct(product);

        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> findAll(Integer cartId){
        return cartItemRepository.findByCartId(cartId);
    }

    public CartItem updateItem(CartItem cartItem) throws CartItemNotFoundException {
        CartItem item = cartItemRepository.findById(cartItem.getId())
                .orElseThrow(()-> new CartItemNotFoundException("Could not find Item"));

        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Integer id) throws CartItemNotFoundException {
        Optional<CartItem> cartToDelete= cartItemRepository.findById(id);
        if(cartToDelete.isPresent()){
            cartItemRepository.delete(cartToDelete.get());
        } else {
            throw new CartItemNotFoundException("CartItem with ID "+ id + "could not be found");
        }
    }

}
