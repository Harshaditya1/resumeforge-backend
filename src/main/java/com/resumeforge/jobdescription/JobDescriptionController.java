package com.resumeforge.jobdescription;

import com.resumeforge.jobdescription.dto.JobDescriptionRequestDto;
import com.resumeforge.jobdescription.dto.JobDescriptionResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/job-descriptions")
@CrossOrigin(origins = "http://localhost:5173")
public class JobDescriptionController {

    private final JobDescriptionService jobDescriptionService;

    public JobDescriptionController(JobDescriptionService jobDescriptionService) {
        this.jobDescriptionService = jobDescriptionService;
    }

    @PostMapping
    public ResponseEntity<JobDescriptionResponseDto> saveJobDescription(
            @Valid @RequestBody JobDescriptionRequestDto request) {

        JobDescriptionResponseDto response =
                jobDescriptionService.saveJobDescription(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<JobDescriptionResponseDto>> getAllJobDescriptions() {

        List<JobDescriptionResponseDto> jobDescriptions =
                jobDescriptionService.getAllJobDescriptions();

        return ResponseEntity.ok(jobDescriptions);
    }
    @GetMapping("/latest")
    public ResponseEntity<JobDescriptionResponseDto> getLatestJobDescription() {

        JobDescriptionResponseDto response =
                jobDescriptionService.getLatestJobDescription();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{jobDescriptionId}")
    public ResponseEntity<JobDescriptionResponseDto> getJobDescriptionById(
            @PathVariable Long jobDescriptionId) {

        JobDescription jobDescription =
                jobDescriptionService.getJobDescriptionById(jobDescriptionId);

        JobDescriptionResponseDto response =
                JobDescriptionResponseDto.builder()
                        .id(jobDescription.getId())
                        .content(jobDescription.getContent())
                        .extractedKeywords(jobDescription.getExtractedKeywords())
                        .createdAt(jobDescription.getCreatedAt())
                        .build();

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{jobDescriptionId}")
    public ResponseEntity<Void> deleteJobDescription(
            @PathVariable Long jobDescriptionId) {

        jobDescriptionService.deleteJobDescription(jobDescriptionId);

        return ResponseEntity.noContent().build();
    }
}