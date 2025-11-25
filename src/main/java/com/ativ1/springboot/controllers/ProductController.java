package com.ativ1.springboot.controllers;

import com.ativ1.springboot.domain.Product;
import com.ativ1.springboot.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//ENDPOINTS DE PRODUTOS
@RestController
@RequestMapping("/produtos")
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }


    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }


    @GetMapping
    public List<Product> List() {
        return repository.findAll();
    }


    @GetMapping("/{id}")
    public Product ListByID(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }


    @PutMapping("/{id}")
    public Product Update(@PathVariable Long id, @RequestBody Product newProduct) {
        return repository.findById(id).map(product -> {
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            product.setQuantity(newProduct.getQuantity());
            return repository.save(product);
        }).orElse(null);
    }


    @DeleteMapping("/{id}")
    public void Delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
