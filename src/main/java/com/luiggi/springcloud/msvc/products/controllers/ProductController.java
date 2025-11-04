package com.luiggi.springcloud.msvc.products.controllers;

// import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luiggi.libs.msvc.commons.entities.Product;
import com.luiggi.springcloud.msvc.products.services.ProductService;

@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    final private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ResponseEntity<?> : viene cualquier cosa, pero sabemos que es una lista de
    // productos por el findAll()
    // public ResponseEntity<List<Product>> list(){
    @GetMapping
    public ResponseEntity<?> list() {
        logger.info("Ingresando al metodo del controller ProductController::list");
        // return ResponseEntity.status(200).body(this.service.findAll());
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) throws InterruptedException {
        logger.info("Ingresando al metodo del controller ProductController::details");

        if (id.equals(10L)) {
            throw new IllegalStateException("Producto no encontrado");
        }
        // simulando una pausa
        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(3L);
        }

        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        logger.info("Ingresando al metodo ProductController::create, creando: {}", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product) {
        logger.info("Ingresando al metodo ProductController::update, actualizando: {}", product);
        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            Product productDb = productOptional.orElseThrow();
            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setCreateAt(product.getCreateAt());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        
        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            this.service.deleteById(id);
            logger.info("Ingresando al metodo ProductController::delete, eliminando: {}", productOptional.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
