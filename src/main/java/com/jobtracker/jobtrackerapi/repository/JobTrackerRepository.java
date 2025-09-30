package com.jobtracker.jobtrackerapi.repository;

import com.jobtracker.jobtrackerapi.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTrackerRepository extends JpaRepository<JobApplication, Integer> {
}
