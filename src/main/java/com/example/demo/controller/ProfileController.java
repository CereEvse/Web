package com.example.demo.controller;

import com.example.demo.model.Resume;
import com.example.demo.model.User;
import com.example.demo.service.ResumeService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/lk")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final ResumeService resumeService;

    @GetMapping
    public String showPersonalAccount(Model model, Authentication authentication) {
        try {
            String username = authentication.getName();
            User currentUser = userService.findByLogin(username)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            // Получаем резюме пользователя
            Optional<Resume> userResume = resumeService.findByUserId(currentUser.getIdUser());

            model.addAttribute("user", currentUser);
            userResume.ifPresentOrElse(
                    resume -> model.addAttribute("resume", resume),
                    () -> model.addAttribute("resume", null)
            );

            return "lk";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при загрузке профиля: " + e.getMessage());
            return "lk";
        }
    }
}