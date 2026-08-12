package com.resumeforge.jobdescription.mapper;

import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.dto.JobDescriptionResponseDto;

public final class JobDescriptionMapper {

    private JobDescriptionMapper() {
    }

    public static JobDescriptionResponseDto toResponseDto(
            JobDescription jobDescription) {

        if (jobDescription == null) {
            return null;
        }

        return JobDescriptionResponseDto.builder()
                .id(jobDescription.getId())
                .content(jobDescription.getContent())
                .extractedKeywords(jobDescription.getExtractedKeywords())
                .createdAt(jobDescription.getCreatedAt())
                .build();
    }
}
