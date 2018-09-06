package com.wiredbraincoffee.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class ProductEvent {
    private Long eventId;
    private String eventType;
}
