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
public class AtsSkillProfile {

    @Builder.Default
    private List<String> languages = new ArrayList<>();

    @Builder.Default
    private List<String> frameworks = new ArrayList<>();

    @Builder.Default
    private List<String> libraries = new ArrayList<>();

    @Builder.Default
    private List<String> databases = new ArrayList<>();

    @Builder.Default
    private List<String> cloud = new ArrayList<>();

    @Builder.Default
    private List<String> devOps = new ArrayList<>();

    @Builder.Default
    private List<String> testing = new ArrayList<>();

    @Builder.Default
    private List<String> messaging = new ArrayList<>();

    @Builder.Default
    private List<String> ai = new ArrayList<>();

    @Builder.Default
    private List<String> tools = new ArrayList<>();

    @Builder.Default
    private List<String> versionControl = new ArrayList<>();

    @Builder.Default
    private List<String> operatingSystems = new ArrayList<>();

    @Builder.Default
    private List<String> concepts = new ArrayList<>();

    @Builder.Default
    private List<String> softSkills = new ArrayList<>();
}