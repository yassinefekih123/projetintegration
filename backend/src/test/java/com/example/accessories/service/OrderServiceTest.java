package com.example.accessories.service;

import com.example.accessories.dto.OrderDto;
import com.example.accessories.entity.Order;
import com.example.accessories.entity.OrderStatus;
import com.example.accessories.entity.User;
import com.example.accessories.mapper.OrderMapper;
import com.example.accessories.repository.AccessoryRepository;
import com.example.accessories.repository.OrderRepository;
import com.example.accessories.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    AccessoryRepository accessoryRepository;
    @Mock
    OrderMapper orderMapper;

    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cancelByOwner_success() {
        UUID id = UUID.randomUUID();
        User owner = User.builder().email("owner@example.com").build();
        Order order = Order.builder().id(id).status(OrderStatus.PENDING).user(owner).build();

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(userRepository.findByEmail(owner.getEmail())).thenReturn(Optional.of(owner));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        OrderDto mapped = OrderDto.builder().id(id).status("CANCELLED").build();
        when(orderMapper.toDto(any(Order.class))).thenReturn(mapped);

        OrderDto res = orderService.cancelOrder(owner.getEmail(), id);

        assertThat(res).isNotNull();
        assertThat(res.getStatus()).isEqualTo("CANCELLED");
        verify(orderRepository).save(argThat(o -> o.getStatus() == OrderStatus.CANCELLED));
    }

    @Test
    void cancelAlreadyCancelled_throws() {
        UUID id = UUID.randomUUID();
        User owner = User.builder().email("owner2@example.com").build();
        Order order = Order.builder().id(id).status(OrderStatus.CANCELLED).user(owner).build();

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.cancelOrder(owner.getEmail(), id))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void cancelByOtherUser_denied() {
        UUID id = UUID.randomUUID();
        User owner = User.builder().email("owner3@example.com").build();
        User other = User.builder().email("other@example.com").role(null).build();
        Order order = Order.builder().id(id).status(OrderStatus.PENDING).user(owner).build();

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(userRepository.findByEmail(other.getEmail())).thenReturn(Optional.of(other));

        assertThatThrownBy(() -> orderService.cancelOrder(other.getEmail(), id))
                .isInstanceOf(org.springframework.security.access.AccessDeniedException.class);
    }
}
