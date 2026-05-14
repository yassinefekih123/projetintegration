package com.example.accessories.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/uploads")
public class UploadController {
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) return ResponseEntity.badRequest().body(java.util.Map.of("error", "No file provided"));

        long maxBytes = 5L * 1024L * 1024L; // 5 MB
        if (file.getSize() > maxBytes) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", "File too large (max 5MB)"));
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/webp"))) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", "Unsupported file type"));
        }

        String original = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = "";
        int i = original.lastIndexOf('.');
        if (i >= 0) ext = original.substring(i);
        String name = UUID.randomUUID().toString() + ext;
        
        try {
            Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(dir)) Files.createDirectories(dir);
            Path target = dir.resolve(name);
            file.transferTo(target);
            String url = "/uploads/" + name;
            return ResponseEntity.ok().body(java.util.Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of("success", false, "message", e.toString()));
        }
    }
}
