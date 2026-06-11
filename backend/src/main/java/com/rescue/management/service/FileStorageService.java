package com.rescue.management.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads/audio/";

    public FileStorageService() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for uploads", e);
        }
    }

    public String saveBase64Audio(String base64Data) {
        if (base64Data == null || !base64Data.contains(",")) {
            return null;
        }

        try {
            String[] parts = base64Data.split(",");
            byte[] audioBytes = Base64.getDecoder().decode(parts[1]);

            String fileName = "voice_" + UUID.randomUUID().toString() + ".webm";
            Path filePath = Paths.get(uploadDir + fileName);

            Files.write(filePath, audioBytes);

            return "/uploads/audio/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store voice file locally", e);
        }
    }
}
