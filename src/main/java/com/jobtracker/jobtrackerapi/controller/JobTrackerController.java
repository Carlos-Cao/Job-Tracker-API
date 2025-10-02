package com.jobtracker.jobtrackerapi.controller;

import com.jobtracker.jobtrackerapi.entity.JobApplication;
import com.jobtracker.jobtrackerapi.service.JobTrackerServiceImp;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
