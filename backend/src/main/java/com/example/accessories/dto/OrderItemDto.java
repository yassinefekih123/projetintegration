package com.example.accessories.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private UUID accessoryId;
    private String accessoryName;
    private Integer quantity;
    private BigDecimal priceSnapshot;
}
