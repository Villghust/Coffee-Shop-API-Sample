package com.wiredbraincoffee.repository;

import com.wiredbraincoffee.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
//    Flux<Product> findByName(String name);
//    Flux<Product> findByNameOrderByPrice(Publisher<String> name);
}
