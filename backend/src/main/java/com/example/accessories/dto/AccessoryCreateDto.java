package com.example.accessories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccessoryCreateDto {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer stockQuantity;

    private String imageUrl;

    private String accessoryType;

    private String compatibility;

    private UUID categoryId;

    private UUID brandId;
}
