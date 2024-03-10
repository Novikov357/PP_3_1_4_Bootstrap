package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDTO {

    private long id;

    private String username;

    private String email;

    private String password;

    private List<String> roles;
}
