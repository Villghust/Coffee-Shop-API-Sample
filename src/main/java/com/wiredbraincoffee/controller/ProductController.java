package com.wiredbraincoffee.controller;

import com.wiredbraincoffee.model.Product;
import com.wiredbraincoffee.model.ProductEvent;
import com.wiredbraincoffee.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@AllArgsConstructor
@RestController
//@Api(value = "ProductControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/products")
public class ProductController {
    private ProductRepository repository;

    @GetMapping
//    @ApiOperation("Gets all the products")
    public Flux<Product> getAllProducts() {
        return repository.findAll();
    }

    @GetMapping("{id}")
//    @ApiOperation("Gets the product with specific ID")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id) {
        return repository.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
//    @ApiOperation("Create a product")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping("{id}")
//    @ApiOperation("Update a product")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable(value = "id") String id,
                                                       @RequestBody Product product) {
        return repository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setPrice(product.getPrice());
                    return repository.save(existingProduct);
                })
                .map(updateProduct -> ResponseEntity.ok(updateProduct))
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Se vier um Mono vazio, retorna um NOT FOUND status
    }

    @DeleteMapping("{id}")
//    @ApiOperation("Delete the product by specific id")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable(value = "id") String id) {
        return repository.findById(id)
                .flatMap(existingProduct ->
                        repository.delete(existingProduct)
                                .then(Mono.just(ResponseEntity.ok().<Void>build())) // then é uma melhor escolha para retornar uma resposta de ok (vazia), quando comparado ao método map
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
//    @ApiOperation("Delete all the products")
    public Mono<Void> deleteAllProducts() {
        return repository.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @ApiOperation("Gets all the products events")
    public Flux<ProductEvent> getProductEvent() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(val ->
                        new ProductEvent(val, "Product Event")
                );
    }
}