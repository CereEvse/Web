package com.example.demo.controller.webs;

import com.example.demo.model.Resume;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/resume")
public class ResumeWebController {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private StatusRepository statusRepository;

    // Просмотр всех резюме (с фильтром по статусу)
    @GetMapping
    public String resumeMain(@RequestParam(required = false) String status, Model model) {
        // Предполагаем, что статус "Принято" имеет id=2
        Status acceptedStatus = statusRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Статус 'Принято' не найден"));

        List<Resume> acceptedResumes = resumeRepository.findByStatus(acceptedStatus);
        model.addAttribute("resumes", acceptedResumes);
        return "resume-main";
    }

    // Форма добавления нового резюме
    @GetMapping("/add")
    public String resumeAdd(Model model) {
        model.addAttribute("statuses", statusRepository.findAll());
        return "resume-add";
    }

    // Сохранение нового резюме
    @PostMapping("/add")
    public String addResume(@RequestParam Integer workExperience,
                            @RequestParam String portfolio,
                            @RequestParam String skills,
                            @RequestParam String comment,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {

        try {
            // 1. Получаем текущего пользователя
            User currentUser = (User) authentication.getPrincipal();

            // 2. Получаем статус "На рассмотрении" (id=1)
            Status pendingStatus = statusRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Статус 'На рассмотрении' не найден"));

            // 3. Создаем резюме
            Resume resume = new Resume();
            resume.setWorkExperience(workExperience);
            resume.setPortfolio(portfolio);
            resume.setSkills(skills);
            resume.setComment(comment);
            resume.setStatus(pendingStatus);
            resume.setUser(currentUser); // Устанавливаем пользователя

            // 4. Сохраняем
            resumeRepository.save(resume);

            redirectAttributes.addFlashAttribute("success", "Резюме успешно создано и отправлено на рассмотрение");
            return "redirect:/lk";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании резюме: " + e.getMessage());
            return "redirect:/resume/add";
        }
    }

    // Просмотр деталей резюме
    @GetMapping("/{id}")
    public String resumeDetails(@PathVariable(value = "id") long id, Model model) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный идентификатор резюме: " + id));
        model.addAttribute("resume", resume);
        model.addAttribute("statuses", statusRepository.findAll());
        return "resume-details";
    }

    // Форма редактирования резюме
    @GetMapping("/{id}/edit")
    public String resumeEdit(@PathVariable(value = "id") long id, Model model, Authentication authentication) {
        try {
            // Получаем текущего пользователя
            User currentUser = (User) authentication.getPrincipal();

            // Находим резюме
            Resume resume = resumeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Резюме не найдено"));

            // Проверяем, принадлежит ли резюме текущему пользователю
            if (!resume.getUser().getIdUser().equals(currentUser.getIdUser())) {
                throw new RuntimeException("Вы не можете редактировать это резюме");
            }

            model.addAttribute("resume", resume);
            return "resume-edit";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/resume/" + id;
        }
    }

    // Обновление резюме
    @PostMapping("/{id}/edit")
    public String resumeUpdate(@PathVariable(value = "id") long id,
                               @ModelAttribute Resume updatedResume,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            // Получаем текущего пользователя
            User currentUser = (User) authentication.getPrincipal();

            // Находим существующее резюме
            Resume existingResume = resumeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Резюме не найдено"));

            // Проверяем принадлежность резюме
            if (!existingResume.getUser().getIdUser().equals(currentUser.getIdUser())) {
                throw new RuntimeException("Вы не можете редактировать это резюме");
            }

            // Обновляем только разрешенные поля
            existingResume.setWorkExperience(updatedResume.getWorkExperience());
            existingResume.setPortfolio(updatedResume.getPortfolio());
            existingResume.setSkills(updatedResume.getSkills());
            existingResume.setComment(updatedResume.getComment());

            // Сохраняем изменения
            resumeRepository.save(existingResume);

            redirectAttributes.addFlashAttribute("success", "Резюме успешно обновлено");
            return "redirect:/resume/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
            return "redirect:/resume/" + id + "/edit";
        }
    }

    // Удаление резюме
    @Transactional
    @PostMapping("/{id}/delete")
    public String resumeDelete(@PathVariable(value = "id") long id,
                               RedirectAttributes redirectAttributes) {
        Resume resume = resumeRepository.findById(id).orElseThrow();
        resumeRepository.delete(resume);
        redirectAttributes.addFlashAttribute("success", "Резюме успешно удалено");
        return "redirect:/resume";
    }

    @GetMapping("/pending")
    public String getPendingResumes(Model model) {
        Status pendingStatus = statusRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Статус 'На рассмотрении' не найден"));

        List<Resume> pendingResumes = resumeRepository.findByStatus(pendingStatus);
        model.addAttribute("resumes", pendingResumes);
        return "resume-pending"; // имя шаблона
    }

    @PostMapping("/{id}/update-status")
    public String updateResumeStatus(@PathVariable Long id,
                                     @RequestParam Long statusId,
                                     RedirectAttributes redirectAttributes) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Резюме не найдено"));
        Status newStatus = statusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Статус не найден"));

        resume.setStatus(newStatus);
        resumeRepository.save(resume);

        redirectAttributes.addFlashAttribute("success",
                "Статус резюме успешно изменен на " + newStatus.getStatusTitle());

        return "redirect:/resume/pending";
    }
}