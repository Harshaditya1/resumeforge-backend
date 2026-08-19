package com.resumeforge.ai;

import com.resumeforge.ai.prompt.AtsSkillExtractionPromptBuilder;
import com.resumeforge.analysis.KeywordExtractorService;
import com.resumeforge.analysis.model.AtsSkillProfile;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.resume.Resume;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtsSkillExtractionService {

    private final CurrentResumeContextService currentResumeContextService;
    private final AtsSkillExtractionPromptBuilder promptBuilder;
    private final AiClientService aiClientService;
    private final KeywordExtractorService keywordExtractorService;

    public AtsSkillExtractionService(
            CurrentResumeContextService currentResumeContextService,
            AtsSkillExtractionPromptBuilder promptBuilder,
            AiClientService aiClientService,
            KeywordExtractorService keywordExtractorService
    ) {
        this.currentResumeContextService = currentResumeContextService;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
        this.keywordExtractorService = keywordExtractorService;
    }

    public AtsSkillProfile extractResumeSkills() {

        Resume resume =
                currentResumeContextService.getLatestResume();

        return extractFromText(
                resume.getExtractedText()
        );
    }

    public AtsSkillProfile extractJobDescriptionSkills() {

        JobDescription jobDescription =
                currentResumeContextService.getLatestJobDescription();

        return extractFromText(
                jobDescription.getContent()
        );
    }

    public AtsSkillProfile extractFromText(String text) {

        String prompt =
                promptBuilder.buildPrompt(text);

        AtsSkillProfile profile =
                aiClientService.ask(
                        prompt,
                        AtsSkillProfile.class
                );

        normalize(profile);

        return profile;
    }

    private void normalize(AtsSkillProfile profile) {

        profile.setLanguages(normalize(profile.getLanguages()));
        profile.setFrameworks(normalize(profile.getFrameworks()));
        profile.setLibraries(normalize(profile.getLibraries()));
        profile.setDatabases(normalize(profile.getDatabases()));
        profile.setCloud(normalize(profile.getCloud()));
        profile.setDevOps(normalize(profile.getDevOps()));
        profile.setTesting(normalize(profile.getTesting()));
        profile.setMessaging(normalize(profile.getMessaging()));
        profile.setAi(normalize(profile.getAi()));
        profile.setTools(normalize(profile.getTools()));
        profile.setVersionControl(normalize(profile.getVersionControl()));
        profile.setOperatingSystems(normalize(profile.getOperatingSystems()));
        profile.setConcepts(normalize(profile.getConcepts()));
        profile.setSoftSkills(normalize(profile.getSoftSkills()));
    }

    private List<String> normalize(List<String> skills) {

        if (skills == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(
                keywordExtractorService.filterKnownSkills(skills)
        );
    }
}