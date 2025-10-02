package com.jobtracker.jobtrackerapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String positionTitle;

    private String companyName;

    private LocalDate dateApplied;

    private ApplicationStatus status;

    private String jobLink;

    private String notes;

    public enum ApplicationStatus {
        APPLIED,
        INTERVIEW,
        OFFERED,
        REJECTED
    }
}

