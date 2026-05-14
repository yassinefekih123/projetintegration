package com.example.accessories.service;

import com.example.accessories.dto.BrandDto;
import com.example.accessories.entity.Brand;
import com.example.accessories.mapper.BrandMapper;
import com.example.accessories.repository.BrandRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandService {
    private final BrandRepository repo;
    private final BrandMapper mapper;

    public BrandService(BrandRepository repo, BrandMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Cacheable("brands")
    public List<BrandDto> listAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<BrandDto> findById(UUID id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @CacheEvict(value = "brands", allEntries = true)
    public BrandDto create(BrandDto dto) {
        Brand entity = mapper.toEntity(dto);
        return mapper.toDto(repo.save(entity));
    }

    @CacheEvict(value = "brands", allEntries = true)
    public Optional<BrandDto> update(UUID id, BrandDto dto) {
        return repo.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setCountry(dto.getCountry());
            existing.setLogoUrl(dto.getLogoUrl());
            return mapper.toDto(repo.save(existing));
        });
    }

    @CacheEvict(value = "brands", allEntries = true)
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
