package com.globant.academy.controller;
import com.globant.academy.dto.CustomerDTO;
import com.globant.academy.exception.CustomerNotFoundException;
import com.globant.academy.mapper.CustomerMapper;
import com.globant.academy.model.Customer;
import com.globant.academy.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper mappers;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper mappers) {
        this.customerService = customerService;
        this.mappers = mappers;
    }

    @PostMapping()
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.createCustomer(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mappers.toDto(customer));
    }

    @GetMapping()
    public ResponseEntity<List<CustomerDTO>> findAll(){
        List<Customer> customerList = customerService.findAll();
        List<CustomerDTO> customerDTOList = customerList.stream()
                .map(customer -> mappers.toDto(customer))
                .toList();
        return customerDTOList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(customerDTOList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Integer id,@RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        Customer customer = customerService.updateCustomer(id,customerDTO);
        return ResponseEntity.ok( mappers.toDto(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id ) throws CustomerNotFoundException {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
    }
}
