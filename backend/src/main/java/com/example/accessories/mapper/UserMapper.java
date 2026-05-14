package com.example.accessories.mapper;

import com.example.accessories.dto.UserDto;
import com.example.accessories.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
