package com.example.accessories.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BrandDto {
    private UUID id;
    private String name;
    private String country;
    private String logoUrl;
}
