package com.example.accessories.mapper;

import com.example.accessories.dto.BrandDto;
import com.example.accessories.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDto toDto(Brand brand);
    Brand toEntity(BrandDto dto);
}
