package com.wiredbraincoffee.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Document
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
}