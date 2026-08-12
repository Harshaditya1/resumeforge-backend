package com.resumeforge.resume;

import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.auth.user.User;
import com.resumeforge.exception.ResourceNotFoundException;
import com.resumeforge.resume.dto.ResumeResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.nio.file.Path;
import com.resumeforge.resume.mapper.ResumeMapper;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final FileStorageService fileStorageService;
    private final PdfTextExtractorService pdfTextExtractorService;
    private final CurrentUserService currentUserService;

    public ResumeService(
            ResumeRepository resumeRepository,
            FileStorageService fileStorageService,
            PdfTextExtractorService pdfTextExtractorService,
            CurrentUserService currentUserService) {

        this.resumeRepository = resumeRepository;
        this.fileStorageService = fileStorageService;
        this.pdfTextExtractorService = pdfTextExtractorService;
        this.currentUserService = currentUserService;
    }

    public ResumeResponseDto uploadResume(MultipartFile file) throws IOException {

        User currentUser = currentUserService.getCurrentUser();

        String storedFileName = fileStorageService.storeFile(file);

        Resume resume = Resume.builder()
                .user(currentUser)
                .originalFileName(file.getOriginalFilename())
                .storedFileName(storedFileName)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .filePath("uploads/" + storedFileName)
                .uploadedAt(LocalDateTime.now())
                .build();

        Resume savedResume = resumeRepository.save(resume);

        String extractedText =
                pdfTextExtractorService.extractText(savedResume.getFilePath());

        savedResume.setExtractedText(extractedText);

        savedResume = resumeRepository.save(savedResume);

        System.out.println("========== EXTRACTED RESUME TEXT ==========");
        System.out.println(extractedText);
        System.out.println("===========================================");

        return ResumeMapper.toResponseDto(savedResume);
    }

    public List<Resume> getAllResumes() {

        Long userId = currentUserService.getCurrentUser().getId();

        return resumeRepository.findAllByUserIdOrderByUploadedAtDesc(userId);
    }

    public Resume getLatestResume() {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        return resumeRepository
                .findFirstByUserIdOrderByUploadedAtDesc(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No resume found."
                        )
                );
    }

    public Resume getResumeById(Long resumeId) {

        Long userId = currentUserService.getCurrentUser().getId();

        return resumeRepository
                .findByIdAndUserId(resumeId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resume not found with id: " + resumeId
                        )
                );
    }
    public void deleteResume(Long resumeId) throws IOException {

        Resume resume = getResumeById(resumeId);

        fileStorageService.deleteFile(resume.getStoredFileName());

        resumeRepository.delete(resume);
    }
    public Path getResumeFile(Long resumeId) {

        Resume resume = getResumeById(resumeId);

        return fileStorageService.getFilePath(
                resume.getStoredFileName()
        );
    }
}