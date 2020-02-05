package com.recruiter.projectsjavaspringrecruiterDB.service;

import com.recruiter.projectsjavaspringrecruiterDB.data.Applicant;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicantService {


    public void save(Applicant applicant) {
    }

    public List<Applicant> findAllApplicants() {
        return null; // Replace with correct code during Module 2 - task 1
    }

    public List<Applicant> findApplicantsSortByFName() {
        return null; // Replace with correct code during Module 2 - task 3
    }

    public List<Applicant> findApplicantsSortByLName() {
        return null; // Replace with correct code during Module 2 - task 5
    }

    public Applicant findApplicantById(Long id) {
        return null; // Replace with correct code during Module 3 - task 1
    }

    public void delete(Long id) {
    }
}
