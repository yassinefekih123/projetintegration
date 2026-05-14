package com.example.accessories.controller;

import com.example.accessories.dto.AccessoryCreateDto;
import com.example.accessories.dto.AccessoryDto;
import com.example.accessories.service.AccessoryService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accessories")
@Tag(name = "Accessories", description = "Operations for managing accessories")
public class AccessoryController {
    private final AccessoryService service;

    public AccessoryController(AccessoryService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List accessories", description = "Search, filter and paginate accessories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paged list of accessories")
    })
    public ResponseEntity<Page<AccessoryDto>> list(@RequestParam(required = false) String q, Pageable pageable) {
        return ResponseEntity.ok(service.search(q, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get accessory", description = "Retrieve accessory by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accessory found"),
            @ApiResponse(responseCode = "404", description = "Accessory not found")
    })
    public ResponseEntity<AccessoryDto> get(@PathVariable UUID id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create accessory", description = "Create a new accessory (admin only)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accessory created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<AccessoryDto> create(@Valid @RequestBody AccessoryCreateDto dto) {
        AccessoryDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update accessory", description = "Update accessory by id (admin only)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accessory updated"),
            @ApiResponse(responseCode = "404", description = "Accessory not found")
    })
    public ResponseEntity<AccessoryDto> update(@PathVariable UUID id, @Valid @RequestBody AccessoryCreateDto dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete accessory", description = "Delete accessory by id (admin only)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Accessory deleted")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
