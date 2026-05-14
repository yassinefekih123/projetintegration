package com.example.accessories.controller;

import com.example.accessories.dto.OrderCreateDto;
import org.springframework.http.HttpStatus;
import com.example.accessories.dto.OrderDto;
import com.example.accessories.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(java.security.Principal user,
                                                @RequestBody OrderCreateDto createDto) {
        OrderDto dto = orderService.createOrder(user.getName(), createDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> listMyOrders(java.security.Principal user) {
        return ResponseEntity.ok(orderService.listOrdersForUser(user.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderDto> cancelOrder(java.security.Principal user, @PathVariable("id") UUID id) {
        OrderDto dto = orderService.cancelOrder(user.getName(), id);
        return ResponseEntity.ok(dto);
    }
}
