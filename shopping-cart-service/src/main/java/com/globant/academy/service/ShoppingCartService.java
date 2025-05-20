package com.globant.academy.service;

import com.globant.academy.config.RabbitMQConfig;
import com.globant.academy.dto.CartItemDTO;
import com.globant.academy.dto.Message;
import com.globant.academy.dto.NotificationDTO;
import com.globant.academy.dto.ShoppingCartDTO;
import com.globant.academy.exception.CartItemNotFoundException;
import com.globant.academy.exception.CustomerNotFoundException;
import com.globant.academy.exception.ShoppingCartNotFoundException;
import com.globant.academy.mapper.CartItemMapper;
import com.globant.academy.mapper.CustomerMapper;
import com.globant.academy.mapper.ProductMapper;
import com.globant.academy.mapper.ShoppingCartMapper;
import com.globant.academy.model.CartItem;
import com.globant.academy.model.Customer;
import com.globant.academy.model.ShoppingCart;
import com.globant.academy.model.Status;
import com.globant.academy.repository.CartItemRepository;
import com.globant.academy.repository.CustomerRepository;
import com.globant.academy.repository.ShoppingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@EnableRabbit
@Service
public class ShoppingCartService {
    private final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartService.class);

    private final ShoppingCartRepository shoppingCartRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final CustomerMapper mapper;
    private final CartItemMapper cartItemMapper;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, CustomerRepository customerRepository, CartItemRepository cartItemRepository, RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig, CustomerMapper mapper, CartItemMapper cartItemMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.customerRepository = customerRepository;
        this.cartItemRepository = cartItemRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.mapper = mapper;
        this.cartItemMapper = cartItemMapper;
        ;
    }

    public ShoppingCart createShoppingCart(Integer customerId) throws CustomerNotFoundException {
        Customer findCustomer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Could not find customer with id: "+ customerId));

        ShoppingCart cart = new ShoppingCart();
        cart.setCustomer(findCustomer);
        cart.setStatus(Status.DRAFT);

        return shoppingCartRepository.save(cart);
    }

    public List<ShoppingCart> findAll(){
        return shoppingCartRepository.findAll();
    }
    @CacheEvict(value = "shoppingCartCache", key = "#cart.id")
    public ShoppingCart updateCart(ShoppingCart cart) throws ShoppingCartNotFoundException, CustomerNotFoundException {
        ShoppingCart findCart = shoppingCartRepository.findById(cart.getId())
                .orElseThrow(()-> new ShoppingCartNotFoundException("Could not find cart with id: " + cart.getId()));

        return shoppingCartRepository.save(cart);
    }

    @CacheEvict(value = "shoppingCartCache", key = "#id")
    public void deleteCart(Integer id) throws ShoppingCartNotFoundException {
        Optional<ShoppingCart> findCart = shoppingCartRepository.findById(id);
        if(findCart.isPresent()){
            shoppingCartRepository.delete(findCart.get());
        }else {
            throw new ShoppingCartNotFoundException("Could not delete shopping cart with id "+ id);
        }
    }

    public ShoppingCart submitCart(Integer id) throws ShoppingCartNotFoundException {
        ShoppingCart findCart = shoppingCartRepository.findById(id)
                .orElseThrow(()-> new ShoppingCartNotFoundException("Could not find cart with id: " + id));

        findCart.setStatus(Status.SUBMITTED);

        shoppingCartRepository.save(findCart);

        Customer customer = findCart.getCustomer();
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setName(customer.getName());
        notificationDTO.setLastname(customer.getLastname());
        notificationDTO.setEmail(customer.getEmail());
        notificationDTO.setPhone(customer.getPhone());
        notificationDTO.setMessage(Message.CART_SUBMITTED);

        LOGGER.info("Sending message to queue...");
        rabbitTemplate.convertAndSend(rabbitMQConfig.NOTIFICATION_QUEUE,notificationDTO);

        return findCart;
    }

    public ShoppingCart getTotal(Integer id) throws ShoppingCartNotFoundException {
        ShoppingCart cart= shoppingCartRepository.findById(id)
                .orElseThrow(()-> new ShoppingCartNotFoundException("Could not find cart"));
        return cart;
    }

    @Cacheable(value = "shoppingCartCache", key = "#id")
    public ShoppingCart findCart(Integer id) throws ShoppingCartNotFoundException {
        return shoppingCartRepository.findById(id)
                .orElseThrow(()-> new ShoppingCartNotFoundException("Could not find cart"));
    }


}