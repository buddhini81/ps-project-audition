package com.recruiter.projectsjavaspringrecruiterDB.data;

import org.springframework.data.domain.Sort;
import java.util.List;

public interface ApplicantRepository {

    //Remove these dummy methods during Module 1 -> task 2
    void save(Applicant applicant);
    List<Applicant> findAll();
    void deleteById(Long id);
    Applicant findById(Long id);
    List<Applicant> findAll(Sort s);
}
