package com.resumeforge.jobdescription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDescriptionResponseDto {

    private Long id;

    private String content;

    private LocalDateTime createdAt;
}