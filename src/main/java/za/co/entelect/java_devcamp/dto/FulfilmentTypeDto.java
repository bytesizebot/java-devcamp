package za.co.entelect.java_devcamp.dto;

import za.co.entelect.java_devcamp.entity.Product;

public record FulfilmentTypeDto(
        String name,
        String description,
        Product product

) {
}
