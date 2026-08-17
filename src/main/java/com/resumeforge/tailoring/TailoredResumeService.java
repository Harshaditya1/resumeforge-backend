package com.resumeforge.tailoring;

import com.resumeforge.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TailoredResumeService {

    private final TailoredResumeRepository tailoredResumeRepository;

    public TailoredResumeService(
            TailoredResumeRepository tailoredResumeRepository
    ) {
        this.tailoredResumeRepository = tailoredResumeRepository;
    }

    /**
     * Save Tailored Resume
     */
    public TailoredResume save(TailoredResume tailoredResume) {
        return tailoredResumeRepository.save(tailoredResume);
    }

    /**
     * Latest Tailored Resume
     */
    public TailoredResume getLatest(Long resumeId) {

        return tailoredResumeRepository
                .findFirstByResumeIdOrderByCreatedAtDesc(resumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No tailored resume found."
                        ));
    }

    /**
     * Tailored Resume History
     */
    public List<TailoredResume> getHistory(Long resumeId) {

        return tailoredResumeRepository
                .findByResumeIdOrderByCreatedAtDesc(resumeId);
    }

    /**
     * Get Tailored Resume By Id
     */
    public TailoredResume getById(Long id) {

        return tailoredResumeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tailored Resume not found."
                        ));
    }

    /**
     * Delete Tailored Resume
     */
    public void delete(Long id) {

        TailoredResume tailoredResume = getById(id);

        tailoredResumeRepository.delete(tailoredResume);
    }
}