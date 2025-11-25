package com.ativ1.springboot.controllers;

import com.ativ1.springboot.domain.Customer;
import com.ativ1.springboot.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//ENDPOINT DE CLIENTES
@RestController
@RequestMapping("/clientes")
public class CustomerController {
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Customer Create(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @GetMapping
    public List<Customer> List() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Customer ListByID(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Customer Update(@PathVariable Long id, @RequestBody Customer newCustomer) {
        return repository.findById(id).map(customer -> {
            customer.setName(newCustomer.getName());
            customer.setEmail(newCustomer.getEmail());
            customer.setAge(newCustomer.getAge());
            customer.setGender(newCustomer.getGender());
            return repository.save(customer);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void Delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
