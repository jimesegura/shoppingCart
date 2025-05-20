package com.globant.academy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.academy.ShoppingCartApp;
import com.globant.academy.dto.CartItemDTO;
import com.globant.academy.dto.ShoppingCartDTO;
import com.globant.academy.exception.CartItemNotFoundException;
import com.globant.academy.exception.CustomerNotFoundException;
import com.globant.academy.exception.ShoppingCartNotFoundException;
import com.globant.academy.mapper.CartItemMapper;
import com.globant.academy.mapper.ProductMapper;
import com.globant.academy.mapper.ShoppingCartMapper;
import com.globant.academy.model.CartItem;
import com.globant.academy.model.Customer;
import com.globant.academy.model.Product;
import com.globant.academy.model.ProductType;
import com.globant.academy.model.ShoppingCart;
import com.globant.academy.model.Status;
import com.globant.academy.service.CartItemService;
import com.globant.academy.service.ProductService;
import com.globant.academy.service.ShoppingCartService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ShoppingCartController.class)
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService cartService;

    @MockBean
    private ProductService productService;

    @MockBean
    private CartItemService cartItemService;

    @MockBean
    private ShoppingCartMapper mapper;

    @MockBean
    private CartItemMapper itemMapper;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private ShoppingCart cart;
    private Product product;
    private CartItem cartItem;
    private ShoppingCartDTO cartDto;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer(1,"Juan", "Perez", LocalDate.of(1989, 12, 13), "juan@email.com", 123456789, 1234);
        cart = new ShoppingCart(1, customer,new ArrayList<>(), Status.DRAFT);
        product= new Product(1, ProductType.ELECTRONIC, BigDecimal.valueOf(200),"TV","description");
        cartItem= new CartItem(1,product,2);

        when(mapper.toDto(cart)).thenReturn(cartDto);
    }

    @Test
    public void givenACustomer_whenCreateCart_thenCartIsCreated() throws Exception, CustomerNotFoundException {

        Mockito.when(cartService.createShoppingCart(1)).thenReturn(cart);

        mockMvc.perform(post("/api/carts/1")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cart)))
                .andExpect(status().isCreated());

        verify(cartService, times(1)).createShoppingCart(1);
    }

    @Test
    public void givenCarts_whenGetAllCarts_thenReturnAllCarts() throws Exception {
        Mockito.when(cartService.findAll()).thenReturn(List.of(cart));

        mockMvc.perform(get("/api/carts"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenACart_whenDeleteCart_thenCartIsDeleted() throws Exception, ShoppingCartNotFoundException {
        Integer cartId=1;
        doNothing().when(cartService).deleteCart(cart.getId());

        mockMvc.perform(delete("/api/carts/1"))
                .andExpect(status().isOk());

        verify(cartService, times(1)).deleteCart(cartId);
    }



    @Test
    public void givenCart_whenGetAllCartItems_thenReturnAllCartItems() throws Exception {
        Mockito.when(cartItemService.findAll(cart.getId())).thenReturn(List.of(cartItem));

        mockMvc.perform(get("/api/carts/1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void givenACartItem_whenDeleteCartItem_thenCartItemIsDeleted() throws Exception, CartItemNotFoundException {
        Integer id=1;
        doNothing().when(cartItemService).deleteCartItem(id);

        mockMvc.perform(delete("/api/carts/1/items/1"))
                .andExpect(status().isOk());

        verify(cartItemService, times(1)).deleteCartItem(id);
    }

    @Test
    public void givenCartId_whenGetAllByCartId_thenReturnAllProductsInCart() throws Exception {
        Integer cartId=1;
        Mockito.when(productService.getProductsByCartId(cartId)).thenReturn(List.of(product));

        mockMvc.perform(get("/api/carts/1/products"))
                .andExpect(status().isOk());
    }
}
