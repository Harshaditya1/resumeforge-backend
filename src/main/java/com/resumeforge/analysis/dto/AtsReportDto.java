package com.resumeforge.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtsReportDto {

    private String overallAssessment;

    private String scoreCategory;

    private List<String> strengths;

    private List<String> improvements;

    private String recommendation;
}