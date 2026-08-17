package com.resumeforge.generatedresume;

import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.generatedresume.dto.GeneratedResumeDetailsResponseDto;
import com.resumeforge.generatedresume.dto.GeneratedResumeHistoryResponseDto;
import com.resumeforge.pdf.PdfResumeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedResumeService {

    private final GeneratedResumeRepository generatedResumeRepository;
    private final CurrentUserService currentUserService;
    private final PdfResumeService pdfResumeService;

    public GeneratedResumeService(
            GeneratedResumeRepository generatedResumeRepository,
            CurrentUserService currentUserService,
            PdfResumeService pdfResumeService
    ) {
        this.generatedResumeRepository = generatedResumeRepository;
        this.currentUserService = currentUserService;
        this.pdfResumeService = pdfResumeService;
    }

    public GeneratedResume save(GeneratedResume generatedResume) {
        return generatedResumeRepository.save(generatedResume);
    }

    public List<GeneratedResumeHistoryResponseDto> getVersionHistory(Long resumeId) {

        Long userId = currentUserService.getCurrentUser().getId();

        return generatedResumeRepository
                .findByResumeIdAndUserIdOrderByCreatedAtDesc(resumeId, userId)
                .stream()
                .map(this::mapToHistoryDto)
                .toList();
    }

    public GeneratedResumeDetailsResponseDto getVersionById(Long versionId) {

        Long userId = currentUserService.getCurrentUser().getId();

        GeneratedResume generatedResume = generatedResumeRepository
                .findByIdAndUserId(versionId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Generated resume version not found with id: " + versionId));

        return GeneratedResumeDetailsResponseDto.builder()
                .id(generatedResume.getId())
                .resumeId(generatedResume.getResume().getId())
                .jobDescriptionId(generatedResume.getJobDescription().getId())
                .generatedResume(generatedResume.getGeneratedResume())
                .createdAt(generatedResume.getCreatedAt())
                .build();
    }

    public byte[] downloadResumePdf(Long versionId) {

        Long userId = currentUserService.getCurrentUser().getId();

        GeneratedResume generatedResume = generatedResumeRepository
                .findByIdAndUserId(versionId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Generated resume version not found with id: " + versionId));

        return pdfResumeService.generateResumePdf(
                generatedResume.getGeneratedResume()
        );
    }
    public GeneratedResumeDetailsResponseDto getLatest() {

        Long userId = currentUserService.getCurrentUser().getId();

        GeneratedResume generatedResume = generatedResumeRepository
                .findFirstByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No generated resume found."
                        ));

        return GeneratedResumeDetailsResponseDto.builder()
                .id(generatedResume.getId())
                .resumeId(generatedResume.getResume().getId())
                .jobDescriptionId(generatedResume.getJobDescription().getId())
                .generatedResume(generatedResume.getGeneratedResume())
                .createdAt(generatedResume.getCreatedAt())
                .build();
    }
    public void delete(Long id) {

        Long userId = currentUserService.getCurrentUser().getId();

        GeneratedResume generatedResume =
                generatedResumeRepository
                        .findByIdAndUserId(id, userId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Generated resume not found."
                                ));

        generatedResumeRepository.delete(generatedResume);
    }

    private GeneratedResumeHistoryResponseDto mapToHistoryDto(
            GeneratedResume generatedResume) {

        return GeneratedResumeHistoryResponseDto.builder()
                .id(generatedResume.getId())
                .resumeId(generatedResume.getResume().getId())
                .jobDescriptionId(generatedResume.getJobDescription().getId())
                .createdAt(generatedResume.getCreatedAt())
                .build();
    }
    public void approve(Long id) {

        Long userId = currentUserService.getCurrentUser().getId();

        GeneratedResume generatedResume =
                generatedResumeRepository
                        .findByIdAndUserId(id, userId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Generated resume not found."
                                ));

        generatedResume.setApproved(true);

        generatedResumeRepository.save(generatedResume);
    }
}