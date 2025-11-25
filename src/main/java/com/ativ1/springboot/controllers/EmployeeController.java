package com.ativ1.springboot.controllers;

import com.ativ1.springboot.domain.Employee;
import com.ativ1.springboot.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

//Endopoint DE FUNCIONARIOS
@RestController
@RequestMapping("/funcionarios")
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Employee> Create(@RequestBody Employee employee) {
        var employeeCreated = repository.save(employee);
        return ResponseEntity.status(201).body(employeeCreated);
    }

    @GetMapping
    public List<Employee> List() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ListById(@PathVariable Long id) {
        var employee =  repository.findById(id);
        if(employee.isPresent()) return ResponseEntity.ok(employee.get());
        return ResponseEntity.status(404).body(Map.of("error", "Empregado não encontrado."));
    }

    @PutMapping("/{id}")
    public Employee Update(@PathVariable Long id, @RequestBody Employee newEmployee) {
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setPosition(newEmployee.getPosition());
            employee.setSalary(newEmployee.getSalary());
            return repository.save(employee);
        }).orElseThrow(()->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Delete(@PathVariable Long id) {
        var employee = repository.findById(id);
        if (employee.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(200)
                    .body(Map.of("message", "Empregado deletado com sucesso."));
        }
        return ResponseEntity
                .status(404)
                .body(Map.of("error", "Empregado não encontrado."));}

}
