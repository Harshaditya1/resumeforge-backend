package com.resumeforge.jobdescription.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobDescriptionRequestDto {

    @NotBlank(message = "Job description content cannot be blank")
    private String content;
}