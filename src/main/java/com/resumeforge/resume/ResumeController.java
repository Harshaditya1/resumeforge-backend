package com.resumeforge.resume;

import com.resumeforge.resume.dto.ResumeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import java.net.MalformedURLException;
import java.nio.file.Path;

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
    public ResponseEntity<ResumeResponseDto> uploadResume(
            @RequestPart("file") MultipartFile file) throws IOException {

        ResumeResponseDto response = resumeService.uploadResume(file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes() {

        List<Resume> resumes = resumeService.getAllResumes();

        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/latest")
    public ResponseEntity<Resume> getLatestResume() {

        Resume resume = resumeService.getLatestResume();

        return ResponseEntity.ok(resume);
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<Resume> getResumeById(
            @PathVariable Long resumeId) {

        Resume resume = resumeService.getResumeById(resumeId);

        return ResponseEntity.ok(resume);
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(
            @PathVariable Long resumeId) throws IOException {

        resumeService.deleteResume(resumeId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{resumeId}/download")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable Long resumeId)
            throws MalformedURLException {

        Resume resume = resumeService.getResumeById(resumeId);

        Path filePath = resumeService.getResumeFile(resumeId);

        Resource resource = new UrlResource(
                filePath.toUri()
        );

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                resume.getOriginalFileName() +
                                "\""
                )
                .contentType(
                        MediaType.parseMediaType(
                                resume.getFileType()
                        )
                )
                .body(resource);
    }
}