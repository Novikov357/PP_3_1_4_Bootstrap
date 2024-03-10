package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.PasswordDTO;
import ru.kata.spring.boot_security.demo.model.UserDTO;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminsController {

    private UserService userService;

    @GetMapping("/users")
    public String printAllUsers(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "allUsers";
    }

    @GetMapping("/edit")
    public String updateUser(@RequestParam Long id, Model model) {
        model.addAttribute(userService.getUser(id));
        return "editUser";
    }

    @PostMapping("/applyapdate")
    public String doUpdate(@ModelAttribute("user") UserDTO user, Model model) {
        userService.updateUser(user);
        model.addAttribute(user);
        return "editUser";
    }

    @GetMapping("/changePassword")
    public String changePass(@RequestParam Long id, Model model) {
        model.addAttribute("passDTO", new PasswordDTO(id, "", ""));
        return "changePass";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("passDTO") PasswordDTO pass, Model model) {
        if (pass.getNewPass().equals(pass.getOldPass())) {
            model.addAttribute("messageFail", "Пароли совпадают");
            return "changePass";
        }
        if (userService.updatePassword(pass)) {
            model.addAttribute("messageSuccess", "Пароль обновлен");
            return "changePass";
        }
        model.addAttribute("messageFail", "Неверный пароль");
        return "changePass";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/addUser")
    public String addUser(@ModelAttribute("user") UserDTO user) {
        return "addUser";
    }

    @PostMapping("/new")
    public String newUser(@ModelAttribute("user") UserDTO user, Model model) {
        try {
            userService.addUser(user);
        } catch (NonUniqueObjectException e) {
            model.addAttribute("message", e.getMessage());
            System.out.println(e.getMessage());
            return "addUser";
        }
        return "redirect:/admin/users";
    }
}
