package com.example.demo.service;

import com.example.demo.model.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    void addResume(Resume resume);
    List<Resume> getAllResumes();
    Optional<Resume> getResumeById(Long id);
    Optional<Resume> putResumeById(Long id, Resume updatedResume);
    void deleteResumeById(Long id);
}
