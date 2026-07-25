package com.resumeforge.analysis;

import com.resumeforge.analysis.dto.AtsReportDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtsReportService {

    public AtsReportDto generateReport(
            double matchPercentage,
            List<String> matchedKeywords,
            List<String>missingKeywords) {

        String scoreCategory;
        String overallAssessment;
        String recommendation;

        if (matchPercentage >= 85) {

            scoreCategory = "Excellent Match";
            overallAssessment =
                    "Your resume is highly aligned with the job description.";
            recommendation =
                    "Your resume is well optimized. Consider applying after a final review.";

        } else if (matchPercentage >= 70) {

            scoreCategory = "Good Match";
            overallAssessment =
                    "Your resume matches most of the important job requirements.";
            recommendation =
                    "Add the missing skills to further improve your ATS score.";

        } else if (matchPercentage >= 50) {

            scoreCategory = "Average Match";
            overallAssessment =
                    "Your resume partially matches the job description.";
            recommendation =
                    "Improve your resume by including more relevant technical skills and projects.";

        } else {

            scoreCategory = "Needs Improvement";
            overallAssessment =
                    "Your resume has a low match with the job description.";
            recommendation =
                    "Update your resume before applying to increase your chances of passing ATS screening.";

        }

        return AtsReportDto.builder()
                .overallAssessment(overallAssessment)
                .scoreCategory(scoreCategory)
                .strengths(matchedKeywords)
                .improvements(missingKeywords)
                .recommendation(recommendation)
                .build();
    }
}