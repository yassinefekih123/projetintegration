package com.example.accessories.mapper;

import com.example.accessories.dto.BrandDto;
import com.example.accessories.entity.Brand;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-14T22:03:39+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Eclipse Adoptium)"
)
@Component
public class BrandMapperImpl implements BrandMapper {

    @Override
    public BrandDto toDto(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandDto brandDto = new BrandDto();

        brandDto.setId( brand.getId() );
        brandDto.setName( brand.getName() );
        brandDto.setCountry( brand.getCountry() );
        brandDto.setLogoUrl( brand.getLogoUrl() );

        return brandDto;
    }

    @Override
    public Brand toEntity(BrandDto dto) {
        if ( dto == null ) {
            return null;
        }

        Brand.BrandBuilder brand = Brand.builder();

        brand.id( dto.getId() );
        brand.name( dto.getName() );
        brand.country( dto.getCountry() );
        brand.logoUrl( dto.getLogoUrl() );

        return brand.build();
    }
}
