package com.globant.academy.controller;

import com.globant.academy.dto.CartItemDTO;
import com.globant.academy.dto.ProductDTO;
import com.globant.academy.dto.ShoppingCartDTO;
import com.globant.academy.exception.CartItemNotFoundException;
import com.globant.academy.exception.CustomerNotFoundException;
import com.globant.academy.exception.ProductNotFoundException;
import com.globant.academy.mapper.CartItemMapper;
import com.globant.academy.mapper.ProductMapper;
import com.globant.academy.mapper.ShoppingCartMapper;
import com.globant.academy.model.CartItem;
import com.globant.academy.model.Product;
import com.globant.academy.model.ShoppingCart;
import com.globant.academy.service.CartItemService;
import com.globant.academy.service.ProductService;
import com.globant.academy.exception.ShoppingCartNotFoundException;
import com.globant.academy.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;
    private final ProductService productService;
    //private final Mapper mappers;
    private final ShoppingCartMapper mappers;
    private final CartItemMapper itemMapper;
    private final ProductMapper productMapper;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, CartItemService cartItemService, ProductService productService, ShoppingCartMapper mappers, CartItemMapper itemMapper, ProductMapper productMapper) {
        this.shoppingCartService = shoppingCartService;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.mappers = mappers;
        this.itemMapper = itemMapper;
        this.productMapper = productMapper;
    }

    @PutMapping("/{cartId}/submit")
    public ResponseEntity<ShoppingCartDTO> submitCart(@PathVariable Integer cartId) throws ShoppingCartNotFoundException {
        ShoppingCart shoppingCart = shoppingCartService.submitCart(cartId);
        return ResponseEntity.ok(mappers.toDto(shoppingCart));
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<ShoppingCartDTO> createCart(@PathVariable Integer customerId) throws CustomerNotFoundException {
            ShoppingCart shoppingCart = shoppingCartService.createShoppingCart(customerId);
            ShoppingCartDTO shoppingCartDTO = mappers.toDto(shoppingCart);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(shoppingCartDTO);
    }

    @PutMapping("/{id}/cart")
    public ResponseEntity<ShoppingCartDTO> updateCart(@PathVariable Integer id, @RequestBody ShoppingCartDTO shoppingCartDTO) throws ShoppingCartNotFoundException, CustomerNotFoundException {
        ShoppingCart shoppingCartToUpdate = mappers.toEntity(shoppingCartDTO);
        shoppingCartToUpdate.setId(id);
        shoppingCartToUpdate.getProductList().stream()
                .forEach(item -> item.setCart(shoppingCartToUpdate));
        ShoppingCart cart = shoppingCartService.updateCart(shoppingCartToUpdate);
        return ResponseEntity.ok(mappers.toDto(cart));
    }

    @PutMapping("/{cartId}/item")
    public ResponseEntity<CartItemDTO> updateItem(@PathVariable Integer cartId, @RequestBody CartItemDTO cartItemDto) throws CartItemNotFoundException, ShoppingCartNotFoundException {
        CartItem cartItem = itemMapper.toEntity(cartItemDto);
        cartItem.setCart(shoppingCartService.findCart(cartId));
        CartItem item = cartItemService.updateItem(cartItem);
        return ResponseEntity.ok(itemMapper.toDto(item));
    }

    @GetMapping()
    public ResponseEntity<List<ShoppingCartDTO>> findAllCarts(){
        List<ShoppingCart> shoppingCarts = shoppingCartService.findAll();
        List<ShoppingCartDTO> shoppingCartDTOS = shoppingCarts.stream()
                .map(cart -> mappers.toDto(cart))
                .collect(Collectors.toList());
        return shoppingCartDTOS.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(shoppingCartDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCart(@PathVariable Integer id) throws ShoppingCartNotFoundException {
            shoppingCartService.deleteCart(id);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/total")
    public BigDecimal getTotalPrice(@PathVariable Integer id) throws ShoppingCartNotFoundException {
        ShoppingCart cart= shoppingCartService.getTotal(id);
        return mappers.toDto(cart).getTotalPrice();
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDTO> createCartItem(@PathVariable Integer cartId, @RequestParam Integer productId, @RequestParam Integer quantity) throws ShoppingCartNotFoundException, ProductNotFoundException {
        CartItem newCartItem = cartItemService.createCartItem(cartId,productId,quantity);
        CartItemDTO cartItem = itemMapper.toDto(newCartItem);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartItem);
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemDTO>> findAllItems(@PathVariable Integer cartId){
        List<CartItem> cartItems = cartItemService.findAll(cartId);
        List<CartItemDTO> cartItemDTOs = cartItems.stream()
                .map(cartItem -> itemMapper.toDto(cartItem))
                .toList();
        return cartItemDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(cartItemDTOs);
    }

    @DeleteMapping("/{cartId}/items/{id}")
    public ResponseEntity deleteItem(@PathVariable Integer id, @PathVariable Integer cartId) throws CartItemNotFoundException {
            cartItemService.deleteCartItem(id);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/{cartId}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByCartId(@PathVariable Integer cartId){
        List<Product>productList = productService.getProductsByCartId(cartId);
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> productMapper.toDto(product))
                .toList();
        return productDTOS.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productDTOS);
    }
}

