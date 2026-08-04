package com.resumeforge.tailoring;

import org.springframework.stereotype.Service;

@Service
public class TailoredResumeService {

    private final TailoredResumeRepository tailoredResumeRepository;

    public TailoredResumeService(TailoredResumeRepository tailoredResumeRepository) {
        this.tailoredResumeRepository = tailoredResumeRepository;
    }

    public TailoredResume save(TailoredResume tailoredResume) {
        return tailoredResumeRepository.save(tailoredResume);
    }
}