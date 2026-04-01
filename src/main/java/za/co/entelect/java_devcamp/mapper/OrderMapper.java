package za.co.entelect.java_devcamp.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import za.co.entelect.java_devcamp.dto.OrderDto;
import za.co.entelect.java_devcamp.dto.OrderItemDto;
import za.co.entelect.java_devcamp.entity.Order;
import za.co.entelect.java_devcamp.entity.OrderItem;

@AllArgsConstructor
@Component
public class OrderMapper {

    private final ProductMapper productMapper;


    public OrderItemDto toOrderItemDto(OrderItem orderItem){
        return new OrderItemDto(
              productMapper.toDto(orderItem.getProduct()));
    }

    public OrderItem toOrderItemEntity(OrderItemDto orderItemDto){
        return new OrderItem(
                productMapper.toEntity(orderItemDto.product()));
    }
    public OrderDto toOrderDto(Order order){
        return new OrderDto(
                order.getCustomerId(),
                order.getCreatedAt(),
                order.getOrderStatus(),
                order.getContractUrl(),
                order.getOrderItems().stream().map(this::toOrderItemDto).toList()
        );
    }

    public Order toOrderEntity(OrderDto orderDto){
        return new Order(
                orderDto.customerId(),
                orderDto.createdAt(),
                orderDto.orderStatus(),
                orderDto.contractUrl(),
                orderDto.orderItemsDto().stream().map(this::toOrderItemEntity).toList()
        );

    }
}
