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
@CrossOrigin(origins = "http://localhost:5173")
public class GeneratedResumeController {

    private final GeneratedResumeService generatedResumeService;

    public GeneratedResumeController(
            GeneratedResumeService generatedResumeService
    ) {
        this.generatedResumeService = generatedResumeService;
    }

    /**
     * Latest Generated Resume
     */
    @Operation(summary = "Get latest generated resume")
    @GetMapping("/latest")
    public GeneratedResumeDetailsResponseDto getLatest() {

        return generatedResumeService.getLatest();
    }

    /**
     * Resume Version History
     */
    @Operation(summary = "Get generated resume version history")
    @GetMapping("/history/{resumeId}")
    public List<GeneratedResumeHistoryResponseDto> getVersionHistory(
            @PathVariable Long resumeId
    ) {

        return generatedResumeService.getVersionHistory(
                resumeId
        );
    }

    /**
     * Resume Version Details
     */
    @Operation(summary = "Get generated resume version details")
    @GetMapping("/{versionId}")
    public GeneratedResumeDetailsResponseDto getVersionById(
            @PathVariable Long versionId
    ) {

        return generatedResumeService.getVersionById(
                versionId
        );
    }

    /**
     * Download Resume PDF
     */
    @Operation(summary = "Download generated resume as PDF")
    @GetMapping("/{versionId}/download")
    public ResponseEntity<byte[]> downloadResumePdf(
            @PathVariable Long versionId
    ) {

        byte[] pdf =
                generatedResumeService.downloadResumePdf(
                        versionId
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=generated-resume.pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    /**
     * Approve Generated Resume
     */
    @Operation(summary = "Approve generated resume")
    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(
            @PathVariable Long id
    ) {

        generatedResumeService.approve(id);

        return ResponseEntity.ok().build();
    }

    /**
     * Delete Generated Resume
     */
    @Operation(summary = "Delete generated resume")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        generatedResumeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}