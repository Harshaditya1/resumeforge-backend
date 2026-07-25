package com.resumeforge.analysis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiTestController {

    private final AiResumeAnalysisService aiResumeAnalysisService;

    public AiTestController(AiResumeAnalysisService aiResumeAnalysisService) {
        this.aiResumeAnalysisService = aiResumeAnalysisService;
    }

    @GetMapping("/test")
    public String testAI() {

        String resume = """
                Java Developer
                Spring Boot
                MySQL
                REST APIs
                """;

        String jobDescription = """
                Looking for a Java Spring Boot developer with
                Spring Boot, PostgreSQL, Docker and Microservices.
                """;

        return aiResumeAnalysisService.generateSuggestions(
                resume,
                jobDescription
        );
    }
}