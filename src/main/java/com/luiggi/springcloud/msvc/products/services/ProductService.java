package com.luiggi.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;

import com.luiggi.springcloud.msvc.products.entities.Product;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);
}
