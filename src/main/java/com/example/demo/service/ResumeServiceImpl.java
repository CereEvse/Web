package com.example.demo.service;

import com.example.demo.exceptions.ExceptionHandler;
import com.example.demo.model.Resume;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResumeServiceImpl implements ResumeService {

    private final ExceptionHandler exceptionHandler;
    private final ResumeRepository resumeRepository;
    private final StatusRepository statusRepository;

    @Override
    public void addResume(Resume resume) {
        Status status = resume.getStatus();
        if (status != null && status.getIdStatus() != null) {
            status = statusRepository.findById(status.getIdStatus()).orElse(null);
        }
        resume.setStatus(status);
        resumeRepository.save(resume);
    }

    @Override
    public List<Resume> getAllResumes() {return resumeRepository.findAll();}

    @Override
    public Optional<Resume> getResumeById(Long id) {return resumeRepository.findById(id);}

    @Override
    public Optional<Resume> putResumeById(Long id, Resume updatedResume) {
        Optional<Resume> existingResume = resumeRepository.findById(id);
        if(existingResume.isPresent()) {
            Resume resumeToUpdate = existingResume.get();
            if(updatedResume.getStatus() != null && updatedResume.getStatus().getIdStatus() != null) {
                Status status = statusRepository.findById(updatedResume.getStatus().getIdStatus()).orElse(null);
                resumeToUpdate.setStatus(status);
            }
            if(updatedResume.getWorkExperience() != null) {
                resumeToUpdate.setWorkExperience(updatedResume.getWorkExperience());
            }
            if(updatedResume.getPortfolio() != null) {
                resumeToUpdate.setPortfolio(updatedResume.getPortfolio());
            }
            if(updatedResume.getSkills() != null) {
                resumeToUpdate.setSkills(updatedResume.getSkills());
            }
            if(updatedResume.getComment() != null) {
                resumeToUpdate.setComment(updatedResume.getComment());
            }
            resumeRepository.save(resumeToUpdate);
        }
        return existingResume;
    }

    @Override
    public void deleteResumeById(Long id) {resumeRepository.deleteById(id);}

    @Override
    public Optional<Resume> findByUserId(Long userId) {
        return resumeRepository.findByUserId(userId);
    }

}
