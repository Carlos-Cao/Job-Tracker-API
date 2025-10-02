package com.jobtracker.jobtrackerapi.service;

import com.jobtracker.jobtrackerapi.entity.JobApplication;
import com.jobtracker.jobtrackerapi.repository.JobTrackerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JobTrackerServiceImpTest {

    @Mock
    private JobTrackerRepository jobTrackerRepository;

    @InjectMocks
    private JobTrackerServiceImp jobTrackerService;

    private JobApplication jobApplication;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        jobApplication = new JobApplication();
        jobApplication.setId(1L);
        jobApplication.setPositionTitle("Customer Service");
        jobApplication.setCompanyName("Company");
        jobApplication.setDateApplied(LocalDate.now());
        jobApplication.setStatus(JobApplication.ApplicationStatus.APPLIED);
        jobApplication.setJobLink("https://jobs.company.com/123");
        jobApplication.setNotes("Job Applied");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testAddJobApplication() {
        when(jobTrackerRepository.save(jobApplication)).thenReturn(jobApplication);

        JobApplication saved = jobTrackerService.addJobApplication(jobApplication);

        assertNotNull(saved);
        assertEquals("Customer Service", saved.getPositionTitle());
        verify(jobTrackerRepository, times(1)).save(jobApplication);
    }

    @Test
    void testGetJobApplicationById_Found() {
        when(jobTrackerRepository.findById(1)).thenReturn(Optional.of(jobApplication));

        Optional<JobApplication> result = jobTrackerService.getJobApplicationById(1);

        assertTrue(result.isPresent());
        assertEquals("Company", result.get().getCompanyName());
        verify(jobTrackerRepository, times(1)).findById(1);
    }

    @Test
    void testGetJobApplicationById_NotFound() {
        when(jobTrackerRepository.findById(2)).thenReturn(Optional.empty());

        Optional<JobApplication> result = jobTrackerService.getJobApplicationById(2);

        assertFalse(result.isPresent());
        verify(jobTrackerRepository, times(1)).findById(2);
    }

    @Test
    void testDeleteJobApplication() {
        doNothing().when(jobTrackerRepository).deleteById(1);

        jobTrackerService.deleteJobApplication(1);

        verify(jobTrackerRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetAllJobApplications() {
        when(jobTrackerRepository.findAll()).thenReturn(Collections.singletonList(jobApplication));

        List<JobApplication> results = jobTrackerService.getAllJobApplications();

        assertEquals(1, results.size());
        assertEquals("Customer Service", results.getFirst().getPositionTitle());
        verify(jobTrackerRepository, times(1)).findAll();
    }
}
