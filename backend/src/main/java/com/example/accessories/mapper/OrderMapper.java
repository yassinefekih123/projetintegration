package com.example.accessories.mapper;

import com.example.accessories.dto.OrderDto;
import com.example.accessories.dto.OrderItemDto;
import com.example.accessories.entity.Order;
import com.example.accessories.entity.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccessoryMapper.class})
public interface OrderMapper {
    @Mapping(source = "status", target = "status")
    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

    default OrderItemDto orderItemToDto(OrderItem item) {
        if (item == null) return null;
        return OrderItemDto.builder()
                .accessoryId(item.getAccessory() != null ? item.getAccessory().getId() : null)
                .accessoryName(item.getAccessory() != null ? item.getAccessory().getName() : null)
                .quantity(item.getQuantity())
                .priceSnapshot(item.getPriceSnapshot())
                .build();
    }
}
