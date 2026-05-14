package com.example.accessories.service;

import com.example.accessories.dto.AuthRequest;
import com.example.accessories.dto.RegisterRequest;
import com.example.accessories.entity.Role;
import com.example.accessories.entity.User;
import com.example.accessories.repository.UserRepository;
import com.example.accessories.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    @InjectMocks
    AuthService authService;

    @Test
    void register_createsUser_andReturnsToken() {
        RegisterRequest req = new RegisterRequest();
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setEmail("john@example.com");
        req.setPassword("secret");

        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> {
            User u = inv.getArgument(0);
            u.setId(UUID.randomUUID());
            return u;
        });
        when(jwtService.generateToken(anyString())).thenReturn("token");

        var res = authService.register(req);
        assertNotNull(res);
        assertEquals("token", res.getAccessToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void authenticate_validCredentials_returnsToken() {
        AuthRequest req = new AuthRequest();
        req.setEmail("jane@example.com");
        req.setPassword("pwd");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(req.getEmail(), null));
        User user = User.builder().email(req.getEmail()).password("encoded").role(Role.CLIENT).build();
        when(userRepository.findByEmail(req.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(req.getEmail())).thenReturn("token2");

        var res = authService.authenticate(req);
        assertNotNull(res);
        assertEquals("token2", res.getAccessToken());
    }
}
