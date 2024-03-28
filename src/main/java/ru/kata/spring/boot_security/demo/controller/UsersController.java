package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UsersController {

    private UserService userService;

    @GetMapping
    public String index(Principal principal, Model model) {
        model.addAttribute("user", userService.findUserByEmail(principal.getName()));
        return "user";
    }
}
