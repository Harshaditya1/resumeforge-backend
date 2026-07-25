package com.resumeforge.analysis;

import com.resumeforge.analysis.dto.ResumeImprovementDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeImprovementService {

    public ResumeImprovementDto generateSuggestions(List<String> missingSkills) {

        List<String> projectSuggestions = new ArrayList<>();
        List<String> summarySuggestions = new ArrayList<>();
        List<String> atsSuggestions = new ArrayList<>();

        if (!missingSkills.isEmpty()) {

            projectSuggestions.add(
                    "Add projects demonstrating: " + String.join(", ", missingSkills));

            summarySuggestions.add(
                    "Include the missing technical skills in your professional summary where applicable.");
        }

        atsSuggestions.add("Use strong action verbs such as Developed, Designed, Implemented and Optimized.");
        atsSuggestions.add("Quantify achievements using numbers wherever possible.");
        atsSuggestions.add("Keep your resume concise and ATS-friendly.");
        atsSuggestions.add("Maintain consistent formatting throughout the resume.");

        return ResumeImprovementDto.builder()
                .missingSkills(missingSkills)
                .projectSuggestions(projectSuggestions)
                .summarySuggestions(summarySuggestions)
                .atsSuggestions(atsSuggestions)
                .build();
    }
}