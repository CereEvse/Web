package com.example.demo.controller.webs;

import com.example.demo.model.Resume;
import com.example.demo.model.Status;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.repository.StatusRepository;
import com.example.demo.service.ResumeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResumeWebController {
    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/resume")
        public String resumeMain(Model model) {
        Iterable<Resume> resumes = resumeRepository.findAll();
        model.addAttribute("resumes", resumes);
        return "resume-main";
    }

    @GetMapping("/resume/add")
    public String resumeAdd(Model model) {
        model.addAttribute("status", statusRepository.findAll());
        return "resume-add";
    }

//    @GetMapping("/status/add")
//    public String statusAdd(Model model) {return "status-add";}
//
//    @PostMapping("/status/add")
//    public String addStatus(@RequestParam String statusTitle, Model model) {
//        Status status = new Status(statusTitle);
//        statusRepository.save(status);
//        return "redirect:/resume/add";
//    }

    @PostMapping("/resume/add")
    public String addResume(@RequestParam Long statusId,
                            @RequestParam Integer workExperience, @RequestParam String portfolio,
                            @RequestParam String skills, @RequestParam String comment, Model model) {
        Status status = statusRepository.findById(statusId).orElseThrow();

        Resume resume = new Resume(workExperience, portfolio, skills, comment, status);
        resumeRepository.save(resume);
        return "redirect:/lk";
    }

    @GetMapping("/resume/{id}")
    public String resumeDetails(@PathVariable(value = "id") long id, Model model) {
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор резюме: " + id));
        model.addAttribute("resume", resume);
        return "resume-details";
    }

    @GetMapping("/resume/{id}/edit")
    public String resumeEdit(@PathVariable(value = "id") long idresume, Model model) {
        Resume resume = resumeRepository.findById(idresume).orElseThrow();
        model.addAttribute("resume", resume);
        model.addAttribute("status", statusRepository.findAll());
        return "resume-edit";
    }

    @PostMapping("/resume/{id}/edit")
    public String resumeUpdate(@PathVariable(value = "id") long idresume,
                               @RequestParam Long statusId,
                               @RequestParam Integer workExperience,
                               @RequestParam String portfolio,
                               @RequestParam String skills,
                               @RequestParam String comment) {
        Resume resume = resumeRepository.findById(idresume).orElseThrow();
        resume.setWorkExperience(workExperience);
        resume.setPortfolio(portfolio);
        resume.setSkills(skills);
        resume.setComment(comment);

        Status status = statusRepository.findById(statusId).orElseThrow();
        resume.setStatus(status);
        resumeRepository.save(resume);
        return "redirect:/lk" + idresume;
    }

    @Transactional
    @PostMapping("/resume/{id}/delete")
    public String resumeDelete(@PathVariable(value = "id") long idResume) {
        Resume resume = resumeRepository.findById(idResume).orElseThrow();
        resumeRepository.delete(resume);
        return "redirect:/lk";
    }

}
