package com.resumeforge.analysis;

import com.resumeforge.analysis.model.AtsSkillProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightedScoreCalculator {

    public double calculateScore(
            AtsSkillProfile resume,
            AtsSkillProfile job
    ) {

        double score = 0.0;

        score += categoryScore(
                resume.getLanguages(),
                job.getLanguages(),
                20
        );

        score += categoryScore(
                resume.getFrameworks(),
                job.getFrameworks(),
                25
        );

        score += categoryScore(
                resume.getDatabases(),
                job.getDatabases(),
                15
        );

        score += categoryScore(
                resume.getDevOps(),
                job.getDevOps(),
                15
        );

        score += categoryScore(
                resume.getCloud(),
                job.getCloud(),
                10
        );

        score += categoryScore(
                resume.getTesting(),
                job.getTesting(),
                10
        );

        score += categoryScore(
                resume.getSoftSkills(),
                job.getSoftSkills(),
                5
        );

        return Math.round(score * 100.0) / 100.0;
    }

    private double categoryScore(
            List<String> resumeSkills,
            List<String> jobSkills,
            double weight
    ) {

        if (jobSkills == null || jobSkills.isEmpty()) {
            return weight;
        }

        long matched = jobSkills.stream()
                .filter(resumeSkills::contains)
                .count();

        return ((double) matched / jobSkills.size()) * weight;
    }
}