package com.resumeforge.analysis;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
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

    public Set<String> extractKeywords(String text) {

        if (text == null || text.isBlank()) {
            return Set.of();
        }

        String cleanedText = text
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", " ");

        return Arrays.stream(cleanedText.split("\\s+"))
                .filter(word -> !word.isBlank())
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}