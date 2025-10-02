package com.jobtracker.jobtrackerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jobtracker.jobtrackerapi.entity.JobApplication;
import com.jobtracker.jobtrackerapi.service.JobTrackerServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobTrackerController.class)
@ExtendWith(SpringExtension.class)
class JobTrackerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobTrackerServiceImp jobTrackerService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        JobTrackerServiceImp jobTrackerService() {
            return Mockito.mock(JobTrackerServiceImp.class);
        }
    }

    private JobApplication createSampleJobApplication() {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(1L);
        jobApplication.setPositionTitle("Customer Service");
        jobApplication.setCompanyName("Company");
        jobApplication.setDateApplied(LocalDate.of(2025, 9, 10));
        jobApplication.setStatus(JobApplication.ApplicationStatus.APPLIED);
        jobApplication.setJobLink("https://jobs.company.com/123");
        jobApplication.setNotes("Job Applied.");
        return jobApplication;
    }

    @Test
    void postJobApplication_created() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JobApplication jobApp = createSampleJobApplication();
        Mockito.when(jobTrackerService.addJobApplication(any(JobApplication.class))).thenReturn(jobApp);

        mockMvc.perform(post("/api/v1/job/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobApp)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.positionTitle").value("Customer Service"))
                .andExpect(jsonPath("$.companyName").value("Company"));
    }

    @Test
    void getJobApplicationById_found() throws Exception {
        JobApplication jobApp = createSampleJobApplication();
        Mockito.when(jobTrackerService.getJobApplicationById(1)).thenReturn(Optional.of(jobApp));

        mockMvc.perform(get("/api/v1/job/application/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.positionTitle").value("Customer Service"))
                .andExpect(jsonPath("$.companyName").value("Company"));
    }

    @Test
    void getJobApplicationById_notFound() throws Exception {
        Mockito.when(jobTrackerService.getJobApplicationById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/job/application/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteJobApplicationById_noContent() throws Exception {
        Mockito.doNothing().when(jobTrackerService).deleteJobApplication(1);

        mockMvc.perform(delete("/api/v1/job/application/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(jobTrackerService).deleteJobApplication(1);
    }

    @Test
    void getJobApplications_success() throws Exception {
        JobApplication jobApp1 = createSampleJobApplication();
        JobApplication jobApp2 = createSampleJobApplication();
        Mockito.when(jobTrackerService.getAllJobApplications()).thenReturn(Arrays.asList(jobApp1, jobApp2));

        mockMvc.perform(get("/api/v1/job/application/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
