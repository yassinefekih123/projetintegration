package com.example.accessories.service;

import com.example.accessories.dto.AccessoryCreateDto;
import com.example.accessories.dto.AccessoryDto;
import com.example.accessories.entity.Accessory;
import com.example.accessories.entity.Brand;
import com.example.accessories.entity.Category;
import com.example.accessories.mapper.AccessoryMapper;
import com.example.accessories.repository.AccessoryRepository;
import com.example.accessories.repository.BrandRepository;
import com.example.accessories.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccessoryService {
    private final AccessoryRepository repo;
    private final BrandRepository brandRepo;
    private final CategoryRepository categoryRepo;
    private final AccessoryMapper mapper;

    public AccessoryService(AccessoryRepository repo, BrandRepository brandRepo, CategoryRepository categoryRepo, AccessoryMapper mapper) {
        this.repo = repo;
        this.brandRepo = brandRepo;
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }

    @Cacheable(value = "accessories")
    public Page<AccessoryDto> search(String q, Pageable pageable) {
        Page<Accessory> page;
        if (q == null || q.isBlank()) {
            page = repo.findAll(pageable);
        } else {
            page = repo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(q, q, pageable);
        }
        return page.map(mapper::toDto);
    }

    public Optional<AccessoryDto> findById(UUID id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @CacheEvict(value = "accessories", allEntries = true)
    public AccessoryDto create(AccessoryCreateDto dto) {
        Accessory entity = mapper.toEntity(dto);
        if (dto.getBrandId() != null) {
            Brand brand = brandRepo.findById(dto.getBrandId()).orElse(null);
            entity.setBrand(brand);
        }
        if (dto.getCategoryId() != null) {
            Category cat = categoryRepo.findById(dto.getCategoryId()).orElse(null);
            entity.setCategory(cat);
        }
        entity.setCreatedAt(OffsetDateTime.now());
        Accessory saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    @CacheEvict(value = "accessories", allEntries = true)
    public Optional<AccessoryDto> update(UUID id, AccessoryCreateDto dto) {
        return repo.findById(id).map(existing -> {
            mapper.updateEntityFromDto(dto, existing);
            if (dto.getBrandId() != null) {
                Brand brand = brandRepo.findById(dto.getBrandId()).orElse(null);
                existing.setBrand(brand);
            }
            if (dto.getCategoryId() != null) {
                Category cat = categoryRepo.findById(dto.getCategoryId()).orElse(null);
                existing.setCategory(cat);
            }
            existing.setUpdatedAt(OffsetDateTime.now());
            return mapper.toDto(repo.save(existing));
        });
    }

    @CacheEvict(value = "accessories", allEntries = true)
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
