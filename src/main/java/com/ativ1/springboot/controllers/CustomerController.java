package com.ativ1.springboot.controllers;

import com.ativ1.springboot.domain.Customer;
import com.ativ1.springboot.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

//ENDPOINT DE CLIENTES
@RestController
@RequestMapping("/clientes")
public class CustomerController {
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        var customerCreated = repository.save(customer);
        return ResponseEntity.status(201).body(customerCreated);
    }

    @GetMapping
    public List<Customer> List() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ListByID(@PathVariable Long id) {
        var customer =  repository.findById(id);
        if(customer.isPresent()) return ResponseEntity.ok(customer.get());
        return ResponseEntity.status(404).body(Map.of("error", "Usuário não encontrado."));
    }

    @PutMapping("/{id}")
    public Customer Update(@PathVariable Long id, @RequestBody Customer newCustomer) {
        return repository.findById(id).map(customer -> {
            customer.setName(newCustomer.getName());
            customer.setEmail(newCustomer.getEmail());
            customer.setAge(newCustomer.getAge());
            customer.setGender(newCustomer.getGender());
            return repository.save(customer);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Delete(@PathVariable Long id) {
        var customer = repository.findById(id);
        if (customer.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(200)
                    .body(Map.of("message", "Usuário deletado com sucesso."));
        }
        return ResponseEntity
                .status(404)
                .body(Map.of("error", "Usuário não encontrado."));
    }
}
