package com.resumeforge.jobdescription;

import com.resumeforge.jobdescription.dto.JobDescriptionRequestDto;
import com.resumeforge.jobdescription.dto.JobDescriptionResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}