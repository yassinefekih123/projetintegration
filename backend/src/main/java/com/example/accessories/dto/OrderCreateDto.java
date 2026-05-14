package com.example.accessories.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateDto {
    private List<OrderItemCreateDto> items;
}
