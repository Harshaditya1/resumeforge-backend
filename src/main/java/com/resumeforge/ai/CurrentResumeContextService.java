package com.resumeforge.ai;

import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.auth.user.User;
import com.resumeforge.exception.ResourceNotFoundException;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.JobDescriptionRepository;
import com.resumeforge.resume.Resume;
import com.resumeforge.resume.ResumeRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrentResumeContextService {

    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final CurrentUserService currentUserService;

    public CurrentResumeContextService(
            ResumeRepository resumeRepository,
            JobDescriptionRepository jobDescriptionRepository,
            CurrentUserService currentUserService
    ) {
        this.resumeRepository = resumeRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.currentUserService = currentUserService;
    }

    /**
     * Latest Resume of logged-in user
     */
    public Resume getLatestResume() {

        User currentUser = currentUserService.getCurrentUser();

        return resumeRepository
                .findFirstByUserIdOrderByUploadedAtDesc(
                        currentUser.getId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No resume uploaded."
                        ));
    }

    /**
     * Latest Job Description of logged-in user
     */
    public JobDescription getLatestJobDescription() {

        User currentUser = currentUserService.getCurrentUser();

        return jobDescriptionRepository
                .findFirstByUserIdOrderByCreatedAtDesc(
                        currentUser.getId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No Job Description found."
                        ));
    }
}