package com.example.accessories.mapper;

import com.example.accessories.dto.CategoryDto;
import com.example.accessories.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto dto);
}
