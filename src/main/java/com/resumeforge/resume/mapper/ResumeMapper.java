package com.resumeforge.resume.mapper;

import com.resumeforge.resume.Resume;
import com.resumeforge.resume.dto.ResumeResponseDto;

public final class ResumeMapper {

    private ResumeMapper() {
    }

    public static ResumeResponseDto toResponseDto(Resume resume) {

        if (resume == null) {
            return null;
        }

        return ResumeResponseDto.builder()
                .id(resume.getId())
                .originalFileName(resume.getOriginalFileName())
                .fileType(resume.getFileType())
                .fileSize(resume.getFileSize())
                .uploadedAt(resume.getUploadedAt())
                .build();
    }
}