package org.example.dipractice.controller;

import org.example.dipractice.annotation.Controller;
import org.example.dipractice.annotation.Inject;
import org.example.dipractice.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }
}
