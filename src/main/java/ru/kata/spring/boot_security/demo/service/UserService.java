package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDTO;

import java.util.List;

public interface UserService extends UserDetailsService {

    void addUser(UserDTO user) throws Exception;

    User getUser(Long id);

    void updateUser(UserDTO user);

    void deleteUser(Long id);

    List<User> getUserList();

    UserDetails loadUserByUsername(String username);

    User findUserByEmail(String email);

    PasswordEncoder getPasswordEncoder();
}
