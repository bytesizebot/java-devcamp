package za.co.entelect.java_devcamp.service;

import za.co.entelect.java_devcamp.dto.OrderDto;

import java.util.List;
public interface IOrderService {

    List<OrderDto> getOrders();

    OrderDto getOrderById(Long id);
}
