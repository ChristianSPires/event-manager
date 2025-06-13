package com.example.eventmanager.controller;

import com.example.eventmanager.dto.UserDto;
import com.example.eventmanager.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public UserDto getUser(Authentication auth) {
        OAuth2User user = (OAuth2User) auth.getPrincipal();
        return new UserDto(user.getAttribute("name"), user.getAttribute("email"), user.getAttribute("picture"));
    }

}
