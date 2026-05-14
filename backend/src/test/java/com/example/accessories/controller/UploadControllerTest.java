package com.example.accessories.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.upload.dir=target/test-uploads")
class UploadControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    WebTestClient webClient;

    @AfterEach
    void cleanup() throws Exception {
        Path dir = Path.of("target/test-uploads");
        if (Files.exists(dir)) {
            Files.walk(dir).sorted((a,b)->b.compareTo(a)).forEach(p->p.toFile().delete());
        }
    }

    @Test
    void uploadImage_returnsUrl() throws Exception {
        byte[] content = new byte[100];
        for (int i = 0; i < content.length; i++) content[i] = (byte) i;

        org.springframework.util.MultiValueMap<String, Object> parts = new org.springframework.util.LinkedMultiValueMap<>();
        org.springframework.core.io.Resource resource = new org.springframework.core.io.ByteArrayResource(content) {
            @Override
            public String getFilename() {
                return "test.jpg";
            }
        };
        
        org.springframework.http.HttpEntity<org.springframework.core.io.Resource> entity = new org.springframework.http.HttpEntity<>(resource);
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        org.springframework.http.HttpEntity<org.springframework.core.io.Resource> filePart = new org.springframework.http.HttpEntity<>(resource, headers);

        parts.add("file", filePart);

        webClient.post()
                .uri("/api/v1/uploads")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(org.springframework.web.reactive.function.BodyInserters.fromMultipartData(parts))
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.url").isNotEmpty();
    }
}