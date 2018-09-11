package com.wiredbraincoffee;

import com.wiredbraincoffee.model.Product;
import com.wiredbraincoffee.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(/*ReactiveMongoOperations operations,*/ ProductRepository repository) {
        return args -> {
            Flux<Product> productFlux = Flux.just(
                    new Product(null,"Big Latter",2.99),
                    new Product(null,"Big Decaf",2.49),
                    new Product(null,"Green Tea",1.99))
                    .flatMap(repository::save); // p -> repository.save(p)

            productFlux
                    .thenMany(repository.findAll())
                    .subscribe(System.out::println); // Printar os produtos na inicialização do Servlet.

            /*operations.collectionExists(Product.class)
                    .flatMap(exist -> exist ? operations.dropCollection(Product.class) : Mono.just(exist))
                    .thenMany(v -> operations.createCollection(Product.class))
                    .thenMany(productFlux)
                    .thenMany(repository.findAll())
                    .subscribe(System.out::println); */
        };
    }
}
