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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessoryServiceTest {
    @Mock
    AccessoryRepository repo;

    @Mock
    BrandRepository brandRepo;

    @Mock
    CategoryRepository categoryRepo;

    @Mock
    AccessoryMapper mapper;

    @InjectMocks
    AccessoryService service;

    @Test
    void search_returnsPagedDtos() {
        Accessory a = Accessory.builder().id(UUID.randomUUID()).name("Test").build();
        Page<Accessory> page = new PageImpl<>(List.of(a));
        when(repo.findAll(any(PageRequest.class))).thenReturn(page);
        AccessoryDto dto = new AccessoryDto();
        dto.setName("Test");
        when(mapper.toDto(a)).thenReturn(dto);

        Page<AccessoryDto> res = service.search(null, PageRequest.of(0, 10));
        assertEquals(1, res.getTotalElements());
        assertEquals("Test", res.getContent().get(0).getName());
    }

    @Test
    void create_savesAccessory_andReturnsDto() {
        AccessoryCreateDto create = new AccessoryCreateDto();
        create.setName("New");
        create.setPrice(BigDecimal.valueOf(9.99));
        create.setStockQuantity(5);
        UUID bId = UUID.randomUUID();
        create.setBrandId(bId);
        UUID cId = UUID.randomUUID();
        create.setCategoryId(cId);

        Accessory entity = new Accessory();
        when(mapper.toEntity(create)).thenReturn(entity);

        Brand b = new Brand(); b.setId(bId);
        when(brandRepo.findById(bId)).thenReturn(Optional.of(b));
        Category c = new Category(); c.setId(cId);
        when(categoryRepo.findById(cId)).thenReturn(Optional.of(c));

        Accessory saved = Accessory.builder().id(UUID.randomUUID()).name("New").build();
        when(repo.save(entity)).thenReturn(saved);
        AccessoryDto out = new AccessoryDto(); out.setId(saved.getId()); out.setName(saved.getName());
        when(mapper.toDto(saved)).thenReturn(out);

        AccessoryDto res = service.create(create);
        assertEquals(out.getId(), res.getId());
        assertEquals("New", res.getName());
    }
}