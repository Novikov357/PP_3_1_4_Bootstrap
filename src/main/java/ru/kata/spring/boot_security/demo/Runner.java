package ru.kata.spring.boot_security.demo;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.UserDTO;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Component
@AllArgsConstructor
public class Runner implements CommandLineRunner {

    private UserService userService;
    private RoleService roleService;

    @Override
    public void run(String... args) {
        UserDTO admin = new UserDTO();
        admin.setUsername("admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword("admin");
        admin.setRoles(List.of("USER", "ADMIN"));

        UserDTO user = new UserDTO();
        user.setUsername("user");
        user.setEmail("user@user.com");
        user.setPassword("user");
        user.setRoles(List.of("USER"));

        if (roleService.findByRoleName("ADMIN") == null) {
            roleService.addRole(new Role("USER"));
            roleService.addRole(new Role("ADMIN"));
        }
        try {
            userService.addUser(admin);
            userService.addUser(user);
        } catch (Exception ignore) {
            try {
                userService.addUser(user);
            } catch (Exception ignore2) {
            }
        }
    }
}
