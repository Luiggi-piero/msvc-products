package com.luiggi.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luiggi.libs.msvc.commons.entities.Product;
import com.luiggi.springcloud.msvc.products.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    // final para que no se cambie la instancia/referencia
    // mucho más limpio que usar autowired
    final private ProductRepository repository; 

    final private Environment environment;

    public ProductServiceImpl(ProductRepository repository, Environment environment){
        this.repository = repository;
        this.environment = environment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return ((List<Product>) repository.findAll()).stream().map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        });
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return this.repository.save(product);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.repository.deleteById(id); 
    }
}
