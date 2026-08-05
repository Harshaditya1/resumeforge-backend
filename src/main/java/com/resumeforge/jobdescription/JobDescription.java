package com.resumeforge.jobdescription;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import com.resumeforge.auth.user.User;

@Entity
@Table(name = "job_descriptions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;


    @Column(columnDefinition = "TEXT")
    private String extractedKeywords;

    private LocalDateTime createdAt;
}