package com.resumeforge.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDTO {

    private long totalResumes;

    private long totalGeneratedResumes;

    private long totalJobDescriptions;

    private long totalTailoredResumes;
}