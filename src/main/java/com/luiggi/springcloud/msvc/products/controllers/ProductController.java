package com.luiggi.springcloud.msvc.products.controllers;

// import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.luiggi.springcloud.msvc.products.entities.Product;
import com.luiggi.springcloud.msvc.products.services.ProductService;


@RestController
public class ProductController {

    final private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ResponseEntity<?> : viene cualquier cosa, pero sabemos que es una lista de productos por el findAll()
    // public ResponseEntity<List<Product>> list(){
    @GetMapping
    public ResponseEntity<?> list(){
        // return ResponseEntity.status(200).body(this.service.findAll());
        return ResponseEntity.ok(this.service.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) {
        Optional<Product> productOptional = service.findById(id);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build(); 
    }
    
}
