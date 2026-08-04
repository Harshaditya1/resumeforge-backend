package com.resumeforge.generatedresume;

import com.resumeforge.generatedresume.dto.GeneratedResumeDetailsResponseDto;
import com.resumeforge.generatedresume.dto.GeneratedResumeHistoryResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedResumeService {

    private final GeneratedResumeRepository generatedResumeRepository;

    public GeneratedResumeService(GeneratedResumeRepository generatedResumeRepository) {
        this.generatedResumeRepository = generatedResumeRepository;
    }

    public GeneratedResume save(GeneratedResume generatedResume) {
        return generatedResumeRepository.save(generatedResume);
    }

    public List<GeneratedResumeHistoryResponseDto> getVersionHistory(Long resumeId) {

        return generatedResumeRepository
                .findByResumeIdOrderByCreatedAtDesc(resumeId)
                .stream()
                .map(this::mapToHistoryDto)
                .toList();
    }

    public GeneratedResumeDetailsResponseDto getVersionById(Long versionId) {

        GeneratedResume generatedResume = generatedResumeRepository
                .findById(versionId)
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

    private GeneratedResumeHistoryResponseDto mapToHistoryDto(
            GeneratedResume generatedResume) {

        return GeneratedResumeHistoryResponseDto.builder()
                .id(generatedResume.getId())
                .resumeId(generatedResume.getResume().getId())
                .jobDescriptionId(generatedResume.getJobDescription().getId())
                .createdAt(generatedResume.getCreatedAt())
                .build();
    }
}