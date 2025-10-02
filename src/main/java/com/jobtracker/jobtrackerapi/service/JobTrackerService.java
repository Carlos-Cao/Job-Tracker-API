package com.jobtracker.jobtrackerapi.service;

import com.jobtracker.jobtrackerapi.entity.JobApplication;

import java.util.List;
import java.util.Optional;

public interface JobTrackerService {

    JobApplication addJobApplication(JobApplication jobApplication);

    Optional<JobApplication> getJobApplicationById(Integer jobApplicationId);

    void deleteJobApplication(Integer jobApplicationId);

    List<JobApplication> getAllJobApplications();
}
