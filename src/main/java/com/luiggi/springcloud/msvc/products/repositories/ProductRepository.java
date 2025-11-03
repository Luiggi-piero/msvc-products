package com.luiggi.springcloud.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;

import com.luiggi.libs.msvc.commons.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
