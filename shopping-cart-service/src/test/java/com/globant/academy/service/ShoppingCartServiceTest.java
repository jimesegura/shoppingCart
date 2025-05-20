package com.globant.academy.service;

import com.globant.academy.config.RabbitMQConfig;
import com.globant.academy.dto.CustomerDTO;
import com.globant.academy.dto.NotificationDTO;
import com.globant.academy.dto.ShoppingCartDTO;
import com.globant.academy.exception.CustomerNotFoundException;
import com.globant.academy.exception.ShoppingCartNotFoundException;
import com.globant.academy.model.CartItem;
import com.globant.academy.model.Customer;
import com.globant.academy.model.ShoppingCart;
import com.globant.academy.model.Status;
import com.globant.academy.repository.CartItemRepository;
import com.globant.academy.repository.CustomerRepository;
import com.globant.academy.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RabbitMQConfig rabbitMQConfig;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private Customer customer;
    private ShoppingCart shoppingCart;
    private ShoppingCartDTO shoppingCartDTO;
    private CartItem cartItem;
    private CustomerDTO customerDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1);
        customer.setName("John");
        customer.setLastname("Doe");

        customerDTO = new CustomerDTO();
        customerDTO.setId(1);
        customerDTO.setName("John");
        customerDTO.setLastname("Doe");

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1);
        shoppingCart.setCustomer(customer);
        shoppingCart.setStatus(Status.DRAFT);

        cartItem = new CartItem();
        cartItem.setId(1);
        cartItem.setProduct(null);
        cartItem.setQuantity(1);
        cartItem.setCart(shoppingCart);

        shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setCustomer(customerDTO);
        shoppingCartDTO.setStatus(Status.SUBMITTED);
    }

    @Test
    public void givenShoppingCart_whenCreateShoppingCart_thenCartIsCreated() throws CustomerNotFoundException {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        ShoppingCart createdCart = shoppingCartService.createShoppingCart(1);

        assertNotNull(createdCart);
        assertEquals(Status.DRAFT, createdCart.getStatus());
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    public void givenShoppingCart_whenCreateShoppingCartCustomerNotFound_thenThrowCustomerNotFoundException() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> shoppingCartService.createShoppingCart(1));
    }


    @Test
    public void givenCart_whenDeleteCart_thenCartIsDeleted() throws ShoppingCartNotFoundException {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.of(shoppingCart));

        shoppingCartService.deleteCart(1);

        verify(shoppingCartRepository, times(1)).delete(shoppingCart);
    }

    @Test
    public void givenCart_whenDeleteCartNotFound_thenTrowShoppingCartNotFoundException() {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ShoppingCartNotFoundException.class, () -> shoppingCartService.deleteCart(1));
    }

    @Test
    public void givenCart_whenSubmitCart_thenCartIsSubmitted() throws ShoppingCartNotFoundException {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        shoppingCartService.submitCart(1);

        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
        verify(rabbitTemplate, times(1)).convertAndSend(eq(rabbitMQConfig.NOTIFICATION_QUEUE), any(NotificationDTO.class));
    }

    @Test
    public void givenCart_whenSubmitCartNotFound_thenThrowShoppingCartNotFoundException() {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ShoppingCartNotFoundException.class, () -> shoppingCartService.submitCart(1));
    }

    @Test
    public void givenCart_whenGetTotal_thenReturnTotalPrice() throws ShoppingCartNotFoundException {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.of(shoppingCart));

        ShoppingCart result = shoppingCartService.getTotal(1);

        assertNotNull(result);
        assertEquals(shoppingCart.getId(), result.getId());

        verify(shoppingCartRepository, times(1)).findById(1);
    }

    @Test
    public void givenCartDoesNotExist_whenGetTotal_thenThrowShoppingCartNotFoundException() {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ShoppingCartNotFoundException.class, () -> shoppingCartService.getTotal(1));

        verify(shoppingCartRepository, times(1)).findById(1);
    }

    @Test
    public void givenCart_whenFindCart_thenReturnCart() throws ShoppingCartNotFoundException {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.of(shoppingCart));

        ShoppingCart result = shoppingCartService.findCart(1);

        assertNotNull(result);
        assertEquals(shoppingCart.getId(), result.getId());

        verify(shoppingCartRepository, times(1)).findById(1);
    }

    @Test
    public void givenCartDoesNotExist_whenFindCart_thenThrowShoppingCartNotFoundException() {
        when(shoppingCartRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ShoppingCartNotFoundException.class, () -> shoppingCartService.findCart(1));

        verify(shoppingCartRepository, times(1)).findById(1);
    }
}

