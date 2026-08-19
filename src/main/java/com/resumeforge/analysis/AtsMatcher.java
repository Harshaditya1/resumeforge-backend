package com.resumeforge.analysis;

import com.resumeforge.analysis.model.AtsSkillProfile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AtsMatcher {

    public Map<String, List<String>> getMatchedSkills(
            AtsSkillProfile resume,
            AtsSkillProfile job
    ) {

        Map<String, List<String>> matched = new LinkedHashMap<>();

        matchCategory("languages", resume.getLanguages(), job.getLanguages(), matched);
        matchCategory("frameworks", resume.getFrameworks(), job.getFrameworks(), matched);
        matchCategory("libraries", resume.getLibraries(), job.getLibraries(), matched);
        matchCategory("databases", resume.getDatabases(), job.getDatabases(), matched);
        matchCategory("cloud", resume.getCloud(), job.getCloud(), matched);
        matchCategory("devOps", resume.getDevOps(), job.getDevOps(), matched);
        matchCategory("testing", resume.getTesting(), job.getTesting(), matched);
        matchCategory("messaging", resume.getMessaging(), job.getMessaging(), matched);
        matchCategory("ai", resume.getAi(), job.getAi(), matched);
        matchCategory("tools", resume.getTools(), job.getTools(), matched);
        matchCategory("versionControl", resume.getVersionControl(), job.getVersionControl(), matched);
        matchCategory("operatingSystems", resume.getOperatingSystems(), job.getOperatingSystems(), matched);
        matchCategory("concepts", resume.getConcepts(), job.getConcepts(), matched);
        matchCategory("softSkills", resume.getSoftSkills(), job.getSoftSkills(), matched);

        return matched;
    }

    public Map<String, List<String>> getMissingSkills(
            AtsSkillProfile resume,
            AtsSkillProfile job
    ) {

        Map<String, List<String>> missing = new LinkedHashMap<>();

        missingCategory("languages", resume.getLanguages(), job.getLanguages(), missing);
        missingCategory("frameworks", resume.getFrameworks(), job.getFrameworks(), missing);
        missingCategory("libraries", resume.getLibraries(), job.getLibraries(), missing);
        missingCategory("databases", resume.getDatabases(), job.getDatabases(), missing);
        missingCategory("cloud", resume.getCloud(), job.getCloud(), missing);
        missingCategory("devOps", resume.getDevOps(), job.getDevOps(), missing);
        missingCategory("testing", resume.getTesting(), job.getTesting(), missing);
        missingCategory("messaging", resume.getMessaging(), job.getMessaging(), missing);
        missingCategory("ai", resume.getAi(), job.getAi(), missing);
        missingCategory("tools", resume.getTools(), job.getTools(), missing);
        missingCategory("versionControl", resume.getVersionControl(), job.getVersionControl(), missing);
        missingCategory("operatingSystems", resume.getOperatingSystems(), job.getOperatingSystems(), missing);
        missingCategory("concepts", resume.getConcepts(), job.getConcepts(), missing);
        missingCategory("softSkills", resume.getSoftSkills(), job.getSoftSkills(), missing);

        return missing;
    }

    private void matchCategory(
            String category,
            List<String> resumeSkills,
            List<String> jobSkills,
            Map<String, List<String>> result
    ) {

        Set<String> matched = new LinkedHashSet<>(resumeSkills);
        matched.retainAll(jobSkills);

        result.put(category, new ArrayList<>(matched));
    }

    private void missingCategory(
            String category,
            List<String> resumeSkills,
            List<String> jobSkills,
            Map<String, List<String>> result
    ) {

        Set<String> missing = new LinkedHashSet<>(jobSkills);
        missing.removeAll(resumeSkills);

        result.put(category, new ArrayList<>(missing));
    }
}