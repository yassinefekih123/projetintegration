package com.example.accessories.service;

import com.example.accessories.dto.OrderCreateDto;
import com.example.accessories.dto.OrderDto;
import com.example.accessories.dto.OrderItemCreateDto;
import com.example.accessories.entity.*;
import com.example.accessories.mapper.OrderMapper;
import com.example.accessories.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AccessoryRepository accessoryRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDto createOrder(String username, OrderCreateDto createDto) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Order order = Order.builder()
                .status(OrderStatus.PENDING)
                .createdAt(OffsetDateTime.now())
                .user(user)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemCreateDto itemDto : createDto.getItems()) {
            Accessory accessory = accessoryRepository.findById(itemDto.getAccessoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Accessory not found: " + itemDto.getAccessoryId()));

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .accessory(accessory)
                    .quantity(itemDto.getQuantity())
                    .priceSnapshot(accessory.getPrice())
                    .build();

            order.getItems().add(item);

            BigDecimal line = accessory.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            total = total.add(line);
        }

        order.setTotalPrice(total);

        Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }

    public List<OrderDto> listOrdersForUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Order> orders = orderRepository.findByUser(user);
        return orderMapper.toDtoList(orders);
    }

    public OrderDto getOrder(UUID id) {
        return orderMapper.toDto(orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found")));
    }

    public OrderDto cancelOrder(String username, UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Order cannot be cancelled");
        }
        User user = userRepository.findByEmail(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        boolean isOwner = order.getUser() != null && username.equals(order.getUser().getEmail());
        boolean isAdmin = user.getRole() != null && user.getRole().name().equals("ADMIN");
        if (!isOwner && !isAdmin) {
            throw new org.springframework.security.access.AccessDeniedException("Not allowed");
        }
        order.setStatus(OrderStatus.CANCELLED);
        Order saved = orderRepository.save(order);
        return orderMapper.toDto(saved);
    }
}
