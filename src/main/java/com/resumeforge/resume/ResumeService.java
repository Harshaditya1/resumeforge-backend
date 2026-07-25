package com.resumeforge.resume;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final FileStorageService fileStorageService;

    public ResumeService(ResumeRepository resumeRepository,
                         FileStorageService fileStorageService) {
        this.resumeRepository = resumeRepository;
        this.fileStorageService = fileStorageService;
    }

    public Resume uploadResume(MultipartFile file) throws IOException {

        String storedFileName = fileStorageService.storeFile(file);

        Resume resume = Resume.builder()
                .originalFileName(file.getOriginalFilename())
                .storedFileName(storedFileName)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .filePath("uploads/" + storedFileName)
                .uploadedAt(LocalDateTime.now())
                .build();

        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }
}