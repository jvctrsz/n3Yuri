package com.ativ1.springboot.controllers;

import com.ativ1.springboot.domain.Customer;
import com.ativ1.springboot.domain.Product;
import com.ativ1.springboot.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;


//ENDPOINTS DE PRODUTOS
@RestController
@RequestMapping("/produtos")
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        var productCreated =  repository.save(product);
    return  ResponseEntity.status(201).body(productCreated);
    }


    @GetMapping
    public List<Product> List() {
        return repository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> ListByID(@PathVariable Long id) {
        var product =  repository.findById(id);
        if(product.isPresent()) return ResponseEntity.ok(product.get());
        return ResponseEntity.status(404).body(Map.of("error", "Produto não encontrado."));
    }


    @PutMapping("/{id}")
    public Product Update(@PathVariable Long id, @RequestBody Product newProduct) {
        return repository.findById(id).map(product -> {
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            product.setQuantity(newProduct.getQuantity());
            return repository.save(product);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<?> Delete(@PathVariable Long id) {
        var product = repository.findById(id);
        if (product.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(200)
                    .body(Map.of("message", "Produto deletado com sucesso."));
        }
        return ResponseEntity
                .status(404)
                .body(Map.of("error", "Produto não encontrado."));}
}
