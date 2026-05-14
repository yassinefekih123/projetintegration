package com.example.accessories.dto;

import com.example.accessories.entity.Role;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String phoneNumber;
    private OffsetDateTime createdAt;
}
