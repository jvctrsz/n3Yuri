package com.ativ1.springboot.controllers;

import com.ativ1.springboot.domain.Employee;
import com.ativ1.springboot.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Endopoint DE FUNCIONARIOS
@RestController
@RequestMapping("/funcionarios")
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Employee Create(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    @GetMapping
    public List<Employee> List() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Employee ListById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Employee Update(@PathVariable Long id, @RequestBody Employee newEmployee) {
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setPosition(newEmployee.getPosition());
            employee.setSalary(newEmployee.getSalary());
            return repository.save(employee);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void Delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
