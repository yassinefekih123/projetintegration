package com.example.accessories.controller;

import com.example.accessories.entity.Accessory;
import com.example.accessories.repository.AccessoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AccessoryRepository accessoryRepository;
    @Autowired
    com.example.accessories.repository.OrderRepository orderRepository;

    @BeforeEach
    void cleanup() {
        orderRepository.deleteAll();
        accessoryRepository.deleteAll();
    }

    @Test
    void placeAndListOrder_flow() {
        // register user
        Map<String, Object> reg = Map.of(
                "firstName", "Test",
                "lastName", "User",
                "email", "orders@test.example",
                "password", "Password123!",
                "phoneNumber", "123456"
        );

        ResponseEntity<Map> regResp = restTemplate.postForEntity("/api/v1/auth/register", reg, Map.class);
        assertThat(regResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = (String) ((Map) regResp.getBody()).get("accessToken");
        assertThat(token).isNotBlank();

        // create accessory directly
        Accessory accessory = Accessory.builder()
                .name("Test Cable")
                .description("Test cable")
                .price(BigDecimal.valueOf(4.99))
                .stockQuantity(50)
                .createdAt(OffsetDateTime.now())
                .build();
        accessory = accessoryRepository.save(accessory);

        // place order
        Map<String, Object> orderReq = Map.of("items", List.of(Map.of("accessoryId", accessory.getId(), "quantity", 2)));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> req = new HttpEntity<>(orderReq, headers);

        ResponseEntity<Map> orderResp = restTemplate.postForEntity("/api/v1/orders", req, Map.class);
        if (!orderResp.getStatusCode().is2xxSuccessful()) {
            System.out.println("Order create failed: status=" + orderResp.getStatusCode() + " body=" + orderResp.getBody());
        }
        assertThat(orderResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(orderResp.getBody()).containsKey("id");

        // list orders
        HttpEntity<Void> listReq = new HttpEntity<>(headers);
        ResponseEntity<List> listResp = restTemplate.exchange("/api/v1/orders", HttpMethod.GET, listReq, List.class);
        assertThat(listResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listResp.getBody()).isNotEmpty();
    }

        @Test
        void cancelOrder_flow() {
        Map<String, Object> reg = Map.of(
            "firstName", "Cancel",
            "lastName", "User",
            "email", "cancel@test.example",
            "password", "Password123!",
            "phoneNumber", "123456"
        );

        ResponseEntity<Map> regResp = restTemplate.postForEntity("/api/v1/auth/register", reg, Map.class);
        assertThat(regResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = (String) ((Map) regResp.getBody()).get("accessToken");
        assertThat(token).isNotBlank();

        Accessory accessory = Accessory.builder()
            .name("Cancel Cable")
            .description("Test")
            .price(BigDecimal.valueOf(9.99))
            .stockQuantity(10)
            .createdAt(OffsetDateTime.now())
            .build();
        accessory = accessoryRepository.save(accessory);

        Map<String, Object> orderReq = Map.of("items", List.of(Map.of("accessoryId", accessory.getId(), "quantity", 1)));
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> req = new HttpEntity<>(orderReq, headers);

        ResponseEntity<Map> orderResp = restTemplate.postForEntity("/api/v1/orders", req, Map.class);
        assertThat(orderResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Object id = orderResp.getBody().get("id");
        assertThat(id).isNotNull();

        // cancel
        ResponseEntity<Map> cancelResp = restTemplate.postForEntity("/api/v1/orders/" + id + "/cancel", new HttpEntity<>(headers), Map.class);
        if (!cancelResp.getStatusCode().is2xxSuccessful()) {
            System.out.println("Cancel failed: status=" + cancelResp.getStatusCode() + " body=" + cancelResp.getBody());
        }
        assertThat(cancelResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cancelResp.getBody()).containsEntry("status", "CANCELLED");
        }
}
