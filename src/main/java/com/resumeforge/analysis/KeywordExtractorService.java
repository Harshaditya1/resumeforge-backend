package com.resumeforge.analysis;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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

    private final Map<String, String> skillCategories = new HashMap<>();

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

            throw new IllegalStateException(
                    "Unable to load multi-word skills.",
                    e
            );
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
                    .filter(line -> !line.startsWith("#"))
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

            throw new IllegalStateException(
                    "Unable to load skill aliases.",
                    e
            );
        }
    }

    @PostConstruct
    private void loadSkillCategories() {

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new ClassPathResource("skills/skill-categories.txt").getInputStream(),
                                StandardCharsets.UTF_8
                        )
                )
        ) {

            reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .filter(line -> !line.startsWith("#"))
                    .forEach(line -> {

                        String[] parts = line.split("=", 2);

                        if (parts.length == 2) {

                            skillCategories.put(
                                    parts[0].trim().toLowerCase(),
                                    parts[1].trim()
                            );
                        }

                    });

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Unable to load skill categories.",
                    e
            );
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

        for (String skill : multiWordSkills) {

            String normalizedSkill = skill.toLowerCase();

            if (remainingText.contains(normalizedSkill)) {

                keywords.add(
                        normalizeSkill(normalizedSkill)
                );

                remainingText =
                        remainingText.replace(
                                normalizedSkill,
                                " "
                        );
            }
        }

        Arrays.stream(remainingText.split("\\s+"))
                .filter(word -> !word.isBlank())
                .filter(word -> !STOP_WORDS.contains(word))
                .map(this::normalizeSkill)
                .forEach(keywords::add);

        return keywords;
    }

    public String normalizeSkill(String skill) {

        if (skill == null || skill.isBlank()) {
            return null;
        }

        String normalized =
                skill.trim().toLowerCase();

        return skillAliases.getOrDefault(
                normalized,
                normalized
        );
    }

    public boolean isKnownSkill(String skill) {

        if (skill == null || skill.isBlank()) {
            return false;
        }

        return skillCategories.containsKey(
                normalizeSkill(skill)
        );
    }

    public String getSkillCategory(String skill) {

        if (skill == null || skill.isBlank()) {
            return null;
        }

        return skillCategories.get(
                normalizeSkill(skill)
        );
    }

    public Set<String> filterKnownSkills(Collection<String> skills) {

        if (skills == null || skills.isEmpty()) {
            return Set.of();
        }

        return skills.stream()
                .map(this::normalizeSkill)
                .filter(Objects::nonNull)
                .filter(this::isKnownSkill)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}