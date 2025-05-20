package com.globant.academy.service;

import com.globant.academy.dto.CartItemDTO;
import com.globant.academy.dto.ProductDTO;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemService cartItemService;

    private CartItemDTO cartItemDTO;
    private ShoppingCart shoppingCart;
    private Product product;
    private CartItem cartItem;
    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1);

        product = new Product();
        product.setId(1);
        product.setName("Product 1");

        productDTO= new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setType(product.getType());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());

        cartItemDTO = new CartItemDTO();
        cartItemDTO.setProduct(productDTO);
        cartItemDTO.setQuantity(2);

        cartItem = new CartItem();
        cartItem.setId(1);
        cartItem.setCart(shoppingCart);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
    }

    @Test
    public void givenCartItem_whenCreate_thenCartItemSuccess() throws ShoppingCartNotFoundException, ProductNotFoundException {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.of(shoppingCart));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItem createdCartItem = cartItemService.createCartItem(1, 1,1);

        assertNotNull(createdCartItem);
        assertEquals(cartItemDTO.getQuantity(), createdCartItem.getQuantity());
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    public void givenCartItem_whenCreateCartItemProductNotFound_thenThrowsShoppingCartNotFoundException() {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ShoppingCartNotFoundException.class, () -> cartItemService.createCartItem(1, 1,1));
    }

    @Test
    public void givenCartItem_whenCreateCartItemSProductNotFound_thenThrowsProductNotFoundException() throws ShoppingCartNotFoundException {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.of(shoppingCart));
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> cartItemService.createCartItem(1, 1,1));
    }

    @Test
    public void testUpdateCartItemCartItemNotFound() {
        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartItemService.updateItem(cartItem));
    }

    @Test
    public void testDeleteCartItemSuccess() throws CartItemNotFoundException {
        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem));

        cartItemService.deleteCartItem(1);

        verify(cartItemRepository, times(1)).delete(cartItem);
    }

    @Test
    public void testDeleteCartItemNotFound() {
        when(cartItemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartItemService.deleteCartItem(1));
    }

}

