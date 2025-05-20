package com.globant.academy.service;

import com.globant.academy.config.RabbitMQConfig;
import com.globant.academy.dto.CustomerDTO;
import com.globant.academy.dto.Message;
import com.globant.academy.dto.NotificationDTO;
import com.globant.academy.exception.CustomerNotFoundException;
import com.globant.academy.mapper.CustomerMapper;
import com.globant.academy.model.Customer;
import com.globant.academy.repository.CustomerRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@EnableRabbit
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    //private final Mapper mappers;
    private final CustomerMapper mappers;
    private final RabbitMQConfig rabbitMQConfig;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper mappers, RabbitMQConfig rabbitMQConfig, RabbitTemplate rabbitTemplate) {
        this.customerRepository = customerRepository;
        this.mappers = mappers;
        this.rabbitMQConfig = rabbitMQConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Customer createCustomer(CustomerDTO customerDTO){
            Customer customer = mappers.toEntity(customerDTO);

             return customerRepository.save(customer);
    }

    public List<Customer>findAll(){
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Integer id, CustomerDTO customer) throws CustomerNotFoundException {
        Customer findCustomer = customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException("Could not find customer with id: "+ id));

        findCustomer.setName(customer.getName());
        findCustomer.setLastname(customer.getLastname());
        findCustomer.setBirthdate(customer.getBirthdate());
        findCustomer.setEmail(customer.getEmail());
        findCustomer.setPhone(customer.getPhone());
        findCustomer.setIdNumber(customer.getIdNumber());


        return customerRepository.save(findCustomer);
    }

    public void deleteCustomer(Integer id) throws CustomerNotFoundException {
        Optional<Customer> findCustomer = customerRepository.findById(id);
        if (findCustomer.isPresent()){
            customerRepository.delete(findCustomer.get());
        }else {
            throw new CustomerNotFoundException("Customer with ID "+ id+" could not be found");
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void sendBirthdayNotification(){
        List<Customer> customerList= findAll();
        customerList.stream()
                .filter(customer -> customer.getBirthdate().equals(LocalDate.now()))
                .forEach(customer -> {
                        NotificationDTO notification = new NotificationDTO();
                        notification.setName(customer.getName());
                        notification.setLastname(customer.getLastname());
                        notification.setEmail(customer.getEmail());
                        notification.setPhone(customer.getPhone());
                        notification.setMessage(Message.BIRTHDAY);

                        rabbitTemplate.convertAndSend(rabbitMQConfig.NOTIFICATION_QUEUE, notification);
                }
                );
    }
}
