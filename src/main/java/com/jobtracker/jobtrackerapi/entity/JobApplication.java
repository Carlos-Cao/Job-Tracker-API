package com.jobtracker.jobtrackerapi.entity;

import java.time.LocalDate;

public class JobApplication {

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

