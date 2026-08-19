package com.resumeforge.analysis;

import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkillNormalizer {

    public List<String> normalize(List<String> skills) {

        if (skills == null || skills.isEmpty()) {
            return List.of();
        }

        Set<String> normalizedSkills = new LinkedHashSet<>();

        for (String skill : skills) {

            if (skill == null) {
                continue;
            }

            String normalized = skill.trim();

            if (normalized.isBlank()) {
                continue;
            }

            normalized = normalized.toLowerCase(Locale.ROOT);

            normalized = normalized.replaceAll("\\s+", " ");

            normalizedSkills.add(normalized);
        }

        return normalizedSkills
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }
}