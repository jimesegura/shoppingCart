package com.globant.academy.mapper;

import com.globant.academy.dto.CartItemDTO;
import com.globant.academy.dto.CustomerDTO;
import com.globant.academy.dto.ProductDTO;
import com.globant.academy.dto.ShoppingCartDTO;
import com.globant.academy.model.Product;
import com.globant.academy.model.ProductType;
import com.globant.academy.model.ShoppingCart;
import com.globant.academy.model.Customer;
import com.globant.academy.model.CartItem;
import com.globant.academy.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class ShoppingCartMapperTest {

        @InjectMocks
        private ShoppingCartMapper shoppingCartMapper;

        @Mock
        private CustomerMapper customerMapper;

        @Mock
        private CartItemMapper cartItemMapper;

        private ShoppingCart shoppingCart;
        private ShoppingCartDTO shoppingCartDTO;
        private Customer customer;
        private CartItem cartItem;
        private List<CartItem> cartItemList;
        private List<CartItemDTO> cartItemDTOList;
        private CustomerDTO customerDTO;
        private ProductDTO productDTO;
        private Product product;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);

            customer = new Customer(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                    "john.doe@example.com", 123456789, 12345678);
            customerDTO = new CustomerDTO(1, "John", "Doe", LocalDate.of(1990, 1, 1),
                    "john.doe@example.com", 123456789, 12345678);

            product = new Product(1, ProductType.ELECTRONIC, BigDecimal.valueOf(100),"product a", "Description");
            productDTO = new ProductDTO(1, ProductType.ELECTRONIC,BigDecimal.valueOf(200),"product B","description");


            cartItem = new CartItem(1, product, 2);
            cartItemList = Arrays.asList(cartItem);

            cartItemDTOList = Arrays.asList(new CartItemDTO(1, productDTO, 2));

            shoppingCart = new ShoppingCart(1, customer, cartItemList);
            shoppingCartDTO = new ShoppingCartDTO(customerDTO,cartItemDTOList,Status.DRAFT);
        }

        @Test
        void givenShoppingCart_whenToDto_thenShoppingCartDTOMappedCorrectly() {

            when(customerMapper.toDto(customer)).thenReturn(shoppingCartDTO.getCustomer());
            when(cartItemMapper.toDTOList(cartItemList)).thenReturn(cartItemDTOList);


            ShoppingCartDTO dto = shoppingCartMapper.toDto(shoppingCart);


            assertEquals(shoppingCart.getId(), dto.getId());
            assertEquals(shoppingCart.getStatus(), dto.getStatus());
            assertNotNull(dto.getCustomer());
            assertEquals(shoppingCartDTO.getProducts(), dto.getProducts());


            verify(customerMapper).toDto(shoppingCart.getCustomer());
            verify(cartItemMapper).toDTOList(shoppingCart.getProductList());
        }

        @Test
        void givenShoppingCartDTO_whenToEntity_thenShoppingCartMappedCorrectly() {

            when(customerMapper.toEntity(shoppingCartDTO.getCustomer())).thenReturn(customer);
            when(cartItemMapper.toEntityList(shoppingCartDTO.getProducts())).thenReturn(cartItemList);


            ShoppingCart entity = shoppingCartMapper.toEntity(shoppingCartDTO);


            assertEquals(shoppingCartDTO.getId(), entity.getId());
            assertEquals(shoppingCartDTO.getStatus(), entity.getStatus());
            assertNotNull(entity.getCustomer());
            assertEquals(shoppingCartDTO.getProducts().size(), entity.getProductList().size());


            verify(customerMapper).toEntity(shoppingCartDTO.getCustomer());
            verify(cartItemMapper).toEntityList(shoppingCartDTO.getProducts());
        }


}
