package com.resumeforge.analysis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeTailoringRequestDto {

    @NotNull
    private Long resumeId;

    @NotNull
    private Long jobDescriptionId;
}