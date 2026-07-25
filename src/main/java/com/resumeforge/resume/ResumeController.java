package com.resumeforge.resume;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "http://localhost:5173")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Resume> uploadResume(
            @RequestPart("file") MultipartFile file) throws IOException {

        Resume savedResume = resumeService.uploadResume(file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedResume);
    }

    @GetMapping
    public List<Resume> getAllResumes() {
        return resumeService.getAllResumes();
    }
}