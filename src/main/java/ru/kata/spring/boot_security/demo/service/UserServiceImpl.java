package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.hibernate.NonUniqueObjectException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.PasswordDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDTO;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private RoleService roleService;

    @Transactional
    public void addUser(UserDTO userDTO) throws NonUniqueObjectException {
        User user1 = userRepository.findUserByUsername(userDTO.getUsername());
        if (user1 != null) {
            throw new NonUniqueObjectException("Not a unique username", user1.getUsername());
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(this.getPasswordEncoder().encode(userDTO.getPassword()));
        List<Role> roles = userDTO
                .getRoles()
                .stream()
                .map(rolename -> roleService.findByRoleName(rolename))
                .collect(Collectors.toList());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    public void updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).get();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles().stream().map(rolename -> roleService
                .findByRoleName(rolename)).collect(Collectors.toList()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional
    public boolean updatePassword(PasswordDTO passwordDTO) {
        User user = userRepository.getById(passwordDTO.getUserId());
        if (this.getPasswordEncoder().matches(passwordDTO.getOldPass(), user.getPassword())) {
            user.setPassword(this.getPasswordEncoder().encode(passwordDTO.getNewPass()));
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getAuthorities());
    }

    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
