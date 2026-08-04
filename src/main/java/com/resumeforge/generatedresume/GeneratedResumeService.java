package com.resumeforge.generatedresume;

import org.springframework.stereotype.Service;

@Service
public class GeneratedResumeService {

    private final GeneratedResumeRepository generatedResumeRepository;

    public GeneratedResumeService(GeneratedResumeRepository generatedResumeRepository) {
        this.generatedResumeRepository = generatedResumeRepository;
    }

    public GeneratedResume save(GeneratedResume generatedResume) {
        return generatedResumeRepository.save(generatedResume);
    }
}