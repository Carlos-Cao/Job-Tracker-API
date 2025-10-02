package com.jobtracker.jobtrackerapi.controller;

import com.jobtracker.jobtrackerapi.entity.JobApplication;
import com.jobtracker.jobtrackerapi.service.JobTrackerServiceImp;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/${api.version}")
public class JobTrackerController {

    private final JobTrackerServiceImp jobTrackerServiceImp;

    public JobTrackerController(JobTrackerServiceImp jobTrackerServiceImp) {
        this.jobTrackerServiceImp = jobTrackerServiceImp;
    }

    @PostMapping(path = "/job/application", produces = "application/json")
    public ResponseEntity<JobApplication> postJobApplication(@Valid @RequestBody JobApplication jobApplication) {
        JobApplication saved = jobTrackerServiceImp.addJobApplication(jobApplication);
        log.info("Saved Job Application with id: {}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping(path = "/job/application/{id}", produces = "application/json")
    public ResponseEntity<JobApplication> getJobApplicationById(@PathVariable Integer id) {
        Optional<JobApplication> application = jobTrackerServiceImp.getJobApplicationById(id);
        if (application.isPresent()) {
            log.info("Fetching Job Application with ID: {}", id);
            return ResponseEntity.ok(application.get());
        } else {
            log.warn("Job Application with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/job/application/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJobApplicationById(@PathVariable Integer id) {
        jobTrackerServiceImp.deleteJobApplication(id);
        log.info("Deleting Job Application with ID: {}", id);
    }

    @GetMapping(path = "/job/application/all", produces = "application/json")
    public List<JobApplication> getJobApplications() {
        List<JobApplication> applications = jobTrackerServiceImp.getAllJobApplications();
        log.info("Fetched all Job Application, count: {}", applications.size());
        return applications;
    }
}
