package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRole(roleName);
    }

    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public List<String> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> role.getRole())
                .collect(Collectors.toList());
    }
}
