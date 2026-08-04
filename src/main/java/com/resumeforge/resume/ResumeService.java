package com.resumeforge.resume;

import com.resumeforge.exception.ResourceNotFoundException;
import com.resumeforge.resume.dto.ResumeResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final FileStorageService fileStorageService;
    private final PdfTextExtractorService pdfTextExtractorService;

    public ResumeService(
            ResumeRepository resumeRepository,
            FileStorageService fileStorageService,
            PdfTextExtractorService pdfTextExtractorService) {

        this.resumeRepository = resumeRepository;
        this.fileStorageService = fileStorageService;
        this.pdfTextExtractorService = pdfTextExtractorService;
    }

    public ResumeResponseDto uploadResume(MultipartFile file) throws IOException {

        String storedFileName = fileStorageService.storeFile(file);

        Resume resume = Resume.builder()
                .originalFileName(file.getOriginalFilename())
                .storedFileName(storedFileName)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .filePath("uploads/" + storedFileName)
                .uploadedAt(LocalDateTime.now())
                .build();

        Resume savedResume = resumeRepository.save(resume);

        String extractedText = pdfTextExtractorService.extractText(savedResume.getFilePath());

        savedResume.setExtractedText(extractedText);

        savedResume = resumeRepository.save(savedResume);

        System.out.println("========== EXTRACTED RESUME TEXT ==========");
        System.out.println(extractedText);
        System.out.println("===========================================");

        return ResumeResponseDto.builder()
                .id(savedResume.getId())
                .originalFileName(savedResume.getOriginalFileName())
                .fileType(savedResume.getFileType())
                .fileSize(savedResume.getFileSize())
                .uploadedAt(savedResume.getUploadedAt())
                .build();
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    public Resume getResumeById(Long resumeId) {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resume not found with id: " + resumeId
                        )
                );
    }
}