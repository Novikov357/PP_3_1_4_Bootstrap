package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class UsersController {

    private UserService userService;

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/user")
    public String getAuthorisedUser(Principal principal, Model model) {
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        return "user";
    }
}
