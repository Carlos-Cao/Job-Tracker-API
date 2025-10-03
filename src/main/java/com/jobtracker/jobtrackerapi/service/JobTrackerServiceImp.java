package com.jobtracker.jobtrackerapi.service;

import com.jobtracker.jobtrackerapi.entity.JobApplication;
import com.jobtracker.jobtrackerapi.repository.JobTrackerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing job applications.
 */
@Service
public class JobTrackerServiceImp implements JobTrackerService {

    private final JobTrackerRepository jobTrackerRepository;

    public JobTrackerServiceImp(final JobTrackerRepository jobTrackerRepository) {
        this.jobTrackerRepository = jobTrackerRepository;
    }

    @Override
    @Transactional
    public JobApplication addJobApplication(final JobApplication jobApplication) {
        return jobTrackerRepository.save(jobApplication);
    }

    @Override
    public Optional<JobApplication> getJobApplicationById(final Integer jobApplicationId) {
        return jobTrackerRepository.findById(jobApplicationId);
    }

    @Override
    @Transactional
    public void deleteJobApplication(final Integer jobApplicationId) {
        jobTrackerRepository.deleteById(jobApplicationId);
    }

    @Override
    public List<JobApplication> getAllJobApplications() {
        return jobTrackerRepository.findAll();
    }
}
