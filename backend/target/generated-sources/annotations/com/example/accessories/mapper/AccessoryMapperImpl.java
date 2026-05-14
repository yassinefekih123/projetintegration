package com.example.accessories.mapper;

import com.example.accessories.dto.AccessoryCreateDto;
import com.example.accessories.dto.AccessoryDto;
import com.example.accessories.entity.Accessory;
import com.example.accessories.entity.Brand;
import com.example.accessories.entity.Category;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-14T22:03:39+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Eclipse Adoptium)"
)
@Component
public class AccessoryMapperImpl implements AccessoryMapper {

    @Override
    public AccessoryDto toDto(Accessory accessory) {
        if ( accessory == null ) {
            return null;
        }

        AccessoryDto accessoryDto = new AccessoryDto();

        accessoryDto.setCategoryId( accessoryCategoryId( accessory ) );
        accessoryDto.setCategoryName( accessoryCategoryName( accessory ) );
        accessoryDto.setBrandId( accessoryBrandId( accessory ) );
        accessoryDto.setBrandName( accessoryBrandName( accessory ) );
        accessoryDto.setId( accessory.getId() );
        accessoryDto.setName( accessory.getName() );
        accessoryDto.setDescription( accessory.getDescription() );
        accessoryDto.setPrice( accessory.getPrice() );
        accessoryDto.setStockQuantity( accessory.getStockQuantity() );
        accessoryDto.setImageUrl( accessory.getImageUrl() );
        accessoryDto.setAccessoryType( accessory.getAccessoryType() );
        accessoryDto.setCompatibility( accessory.getCompatibility() );
        accessoryDto.setCreatedAt( accessory.getCreatedAt() );
        accessoryDto.setUpdatedAt( accessory.getUpdatedAt() );

        return accessoryDto;
    }

    @Override
    public Accessory toEntity(AccessoryCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        Accessory.AccessoryBuilder accessory = Accessory.builder();

        accessory.name( dto.getName() );
        accessory.description( dto.getDescription() );
        accessory.price( dto.getPrice() );
        accessory.stockQuantity( dto.getStockQuantity() );
        accessory.imageUrl( dto.getImageUrl() );
        accessory.accessoryType( dto.getAccessoryType() );
        accessory.compatibility( dto.getCompatibility() );

        return accessory.build();
    }

    @Override
    public void updateEntityFromDto(AccessoryCreateDto dto, Accessory entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }
        if ( dto.getStockQuantity() != null ) {
            entity.setStockQuantity( dto.getStockQuantity() );
        }
        if ( dto.getImageUrl() != null ) {
            entity.setImageUrl( dto.getImageUrl() );
        }
        if ( dto.getAccessoryType() != null ) {
            entity.setAccessoryType( dto.getAccessoryType() );
        }
        if ( dto.getCompatibility() != null ) {
            entity.setCompatibility( dto.getCompatibility() );
        }
    }

    private UUID accessoryCategoryId(Accessory accessory) {
        if ( accessory == null ) {
            return null;
        }
        Category category = accessory.getCategory();
        if ( category == null ) {
            return null;
        }
        UUID id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String accessoryCategoryName(Accessory accessory) {
        if ( accessory == null ) {
            return null;
        }
        Category category = accessory.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private UUID accessoryBrandId(Accessory accessory) {
        if ( accessory == null ) {
            return null;
        }
        Brand brand = accessory.getBrand();
        if ( brand == null ) {
            return null;
        }
        UUID id = brand.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String accessoryBrandName(Accessory accessory) {
        if ( accessory == null ) {
            return null;
        }
        Brand brand = accessory.getBrand();
        if ( brand == null ) {
            return null;
        }
        String name = brand.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
