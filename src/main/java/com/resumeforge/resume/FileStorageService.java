package com.resumeforge.resume;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    public String storeFile(MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();

        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String storedFileName = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(storedFileName),
                StandardCopyOption.REPLACE_EXISTING
        );

        return storedFileName;
    }
}