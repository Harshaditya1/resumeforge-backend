package com.resumeforge.analysis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StructuredResume {

    private String name;

    @Builder.Default
    private List<String> education = new ArrayList<>();

    @Builder.Default
    private List<String> experience = new ArrayList<>();

    @Builder.Default
    private List<String> projects = new ArrayList<>();

    @Builder.Default
    private List<String> technicalSkills = new ArrayList<>();

    @Builder.Default
    private List<String> softSkills = new ArrayList<>();

    @Builder.Default
    private List<String> certifications = new ArrayList<>();

    @Builder.Default
    private List<String> achievements = new ArrayList<>();

    @Builder.Default
    private List<String> leadership = new ArrayList<>();
}