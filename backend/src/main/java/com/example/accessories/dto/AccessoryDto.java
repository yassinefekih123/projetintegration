package com.example.accessories.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class AccessoryDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private String accessoryType;
    private String compatibility;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private UUID categoryId;
    private String categoryName;
    private UUID brandId;
    private String brandName;
}
