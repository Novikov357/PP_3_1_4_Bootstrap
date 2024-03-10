package ru.kata.spring.boot_security.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordDTO {
    private Long userId;

    private String oldPass;

    private String newPass;
}
