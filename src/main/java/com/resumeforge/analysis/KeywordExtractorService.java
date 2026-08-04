package com.resumeforge.analysis;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@Service
public class KeywordExtractorService {

    private static final Set<String> STOP_WORDS = Set.of(
            "the", "a", "an", "and", "or", "but",
            "is", "are", "was", "were",
            "to", "of", "in", "on", "at", "for",
            "with", "by", "from", "as",
            "we", "our", "you", "your",
            "looking", "required", "requirements"
    );

    private final List<String> multiWordSkills = new ArrayList<>();
    private final Map<String, String> skillAliases = new HashMap<>();

    @PostConstruct
    private void loadMultiWordSkills() {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new ClassPathResource("skills/multi-word-skills.txt").getInputStream(),
                                StandardCharsets.UTF_8
                        )
                )
        ) {

            reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .sorted(Comparator.comparingInt(String::length).reversed())
                    .forEach(multiWordSkills::add);

        } catch (Exception e) {
            throw new IllegalStateException("Unable to load multi-word skills.", e);
        }
    }
    @PostConstruct
    private void loadSkillAliases() {

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new ClassPathResource("skills/skill-aliases.txt").getInputStream(),
                                StandardCharsets.UTF_8
                        )
                )
        ) {

            reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .forEach(line -> {

                        String[] parts = line.split("=", 2);

                        if (parts.length == 2) {
                            skillAliases.put(
                                    parts[0].trim().toLowerCase(),
                                    parts[1].trim().toLowerCase()
                            );
                        }
                    });

        } catch (Exception e) {
            throw new IllegalStateException("Unable to load skill aliases.", e);
        }
    }

    public Set<String> extractKeywords(String text) {

        if (text == null || text.isBlank()) {
            return Set.of();
        }

        String cleanedText = text
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", " ");

        Set<String> keywords = new LinkedHashSet<>();

        String remainingText = cleanedText;

        // Preserve multi-word skills first
        for (String skill : multiWordSkills) {

            String normalizedSkill = skill.toLowerCase();

            if (remainingText.contains(normalizedSkill)) {

                keywords.add(
                        skillAliases.getOrDefault(normalizedSkill, normalizedSkill)
                );

                remainingText = remainingText.replace(normalizedSkill, " ");
            }
        }

        // Extract remaining single words
        Arrays.stream(remainingText.split("\\s+"))
                .filter(word -> !word.isBlank())
                .filter(word -> !STOP_WORDS.contains(word))
                .map(word -> skillAliases.getOrDefault(word, word))
                .forEach(keywords::add);

        return keywords;
    }
}