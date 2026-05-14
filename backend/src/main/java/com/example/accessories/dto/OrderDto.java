package com.example.accessories.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private UUID id;
    private BigDecimal totalPrice;
    private String status;
    private OffsetDateTime createdAt;
    private List<OrderItemDto> items;
}
