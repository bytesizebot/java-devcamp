package za.co.entelect.java_devcamp.dto;

import za.co.entelect.java_devcamp.entity.Status;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long customerId,
        LocalDateTime createdAt,
        Status orderStatus,
        String contractUrl,
        List<OrderItemDto> orderItemsDto
)  {
}
