package com.resumeforge.generatedresume;

import com.resumeforge.generatedresume.dto.GeneratedResumeDetailsResponseDto;
import com.resumeforge.generatedresume.dto.GeneratedResumeHistoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generated-resumes")
public class GeneratedResumeController {

    private final GeneratedResumeService generatedResumeService;

    public GeneratedResumeController(
            GeneratedResumeService generatedResumeService
    ) {
        this.generatedResumeService = generatedResumeService;
    }

    @Operation(summary = "Get generated resume version history by Resume ID")
    @GetMapping("/history/{resumeId}")
    public List<GeneratedResumeHistoryResponseDto> getVersionHistory(
            @PathVariable Long resumeId) {

        return generatedResumeService.getVersionHistory(resumeId);
    }

    @Operation(summary = "Get generated resume version details by Version ID")
    @GetMapping("/{versionId}")
    public GeneratedResumeDetailsResponseDto getVersionById(
            @PathVariable Long versionId) {

        return generatedResumeService.getVersionById(versionId);
    }

    @Operation(summary = "Download generated resume as PDF")
    @GetMapping("/{versionId}/download")
    public ResponseEntity<byte[]> downloadResumePdf(
            @PathVariable Long versionId) {

        byte[] pdf = generatedResumeService.downloadResumePdf(versionId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=generated-resume.pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}