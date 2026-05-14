package com.example.accessories.mapper;

import com.example.accessories.dto.OrderDto;
import com.example.accessories.dto.OrderItemDto;
import com.example.accessories.entity.Order;
import com.example.accessories.entity.OrderItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-14T22:03:39+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto.OrderDtoBuilder orderDto = OrderDto.builder();

        if ( order.getStatus() != null ) {
            orderDto.status( order.getStatus().name() );
        }
        orderDto.id( order.getId() );
        orderDto.totalPrice( order.getTotalPrice() );
        orderDto.createdAt( order.getCreatedAt() );
        orderDto.items( orderItemListToOrderItemDtoList( order.getItems() ) );

        return orderDto.build();
    }

    @Override
    public List<OrderDto> toDtoList(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>( orders.size() );
        for ( Order order : orders ) {
            list.add( toDto( order ) );
        }

        return list;
    }

    protected List<OrderItemDto> orderItemListToOrderItemDtoList(List<OrderItem> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemDto> list1 = new ArrayList<OrderItemDto>( list.size() );
        for ( OrderItem orderItem : list ) {
            list1.add( orderItemToDto( orderItem ) );
        }

        return list1;
    }
}
