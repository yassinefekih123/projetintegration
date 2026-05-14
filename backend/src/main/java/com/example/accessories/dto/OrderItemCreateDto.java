package com.example.accessories.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemCreateDto {
    private UUID accessoryId;
    private Integer quantity;
}
