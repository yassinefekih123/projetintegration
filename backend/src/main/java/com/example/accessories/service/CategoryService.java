package com.example.accessories.service;

import com.example.accessories.dto.CategoryDto;
import com.example.accessories.entity.Category;
import com.example.accessories.mapper.CategoryMapper;
import com.example.accessories.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository repo;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repo, CategoryMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Cacheable("categories")
    public List<CategoryDto> listAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<CategoryDto> findById(UUID id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @CacheEvict(value = "categories", allEntries = true)
    public CategoryDto create(CategoryDto dto) {
        Category entity = mapper.toEntity(dto);
        return mapper.toDto(repo.save(entity));
    }

    @CacheEvict(value = "categories", allEntries = true)
    public Optional<CategoryDto> update(UUID id, CategoryDto dto) {
        return repo.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            return mapper.toDto(repo.save(existing));
        });
    }

    @CacheEvict(value = "categories", allEntries = true)
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
