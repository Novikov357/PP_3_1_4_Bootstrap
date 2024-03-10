package ru.kata.spring.boot_security.demo;

import lombok.AllArgsConstructor;
import org.hibernate.NonUniqueObjectException;
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
    public void run(String... args) throws Exception {
        if (roleService.findByRoleName("ADMIN") == null) {
            roleService.addRole(new Role("USER"));
            roleService.addRole(new Role("ADMIN"));

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("admin");
            userDTO.setEmail("admin@admin.com");
            userDTO.setPassword("admin");
            userDTO.setRoles(List.of("USER", "ADMIN"));

            try {
                userService.addUser(userDTO);
            } catch (NonUniqueObjectException ignore) {
            }
        }
    }
}
