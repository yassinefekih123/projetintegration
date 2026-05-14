package com.example.accessories.controller;

import com.example.accessories.dto.BrandDto;
import com.example.accessories.service.BrandService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/brands")
@Tag(name = "Brands", description = "Manage brands")
public class BrandController {
    private final BrandService service;

    public BrandController(BrandService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List brands", description = "Retrieve brands list")
    public ResponseEntity<List<BrandDto>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get brand", description = "Get brand by id")
    public ResponseEntity<BrandDto> get(@PathVariable UUID id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create brand", description = "Create a new brand")
    public ResponseEntity<BrandDto> create(@Valid @RequestBody BrandDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update brand", description = "Update brand by id")
    public ResponseEntity<BrandDto> update(@PathVariable UUID id, @Valid @RequestBody BrandDto dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete brand", description = "Delete brand by id")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
