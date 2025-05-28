package com.example.demo.controller.webs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/home")
    public String home(Model model){
        return "home";

    }
    @GetMapping("/login")
    public String login(){
        return "enter";

    }
    @GetMapping("/registration")
    public String regist(Model model){
        return "regist";

    }
    @GetMapping("/")
    public String anon(Model model){
        return "anon";

    }

}