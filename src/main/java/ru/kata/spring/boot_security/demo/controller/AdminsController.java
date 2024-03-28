package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDTO;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminsController {

    private UserService userService;
    private RoleService roleService;

    @GetMapping
    public String mainPage(Principal principal, Model model, Session session, HttpServletRequest request,
                           @RequestParam(value = "message", required = false) String message) {
        User user = userService.findUserByEmail(principal.getName());
        if (user == null) {
            try {
                request.logout();
                session.setPersistent(false);
                return "redirect:/?info&?logout";
            } catch (ServletException e) {
                System.out.println(e.getMessage());
            }
        }
        model.addAttribute("message", message != null);
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(@ModelAttribute("user") UserDTO userDTO, Model model) {
        List<String> rolesList = List.of("USER", "ADMIN");
        model.addAttribute("roles", rolesList);
        userService.updateUser(userDTO);
        model.addAttribute(userDTO);
        return "redirect:/admin";
    }

    @PostMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/addUser")
    public String newUser(@ModelAttribute("user") UserDTO user) {
        try {
            userService.addUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/admin?message";
        }
        return "redirect:/admin";
    }
}
