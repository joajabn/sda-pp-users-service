package com.sda.controller;

import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.exception.UsernameConflictException;
import com.sda.model.User;
import com.sda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public void findAll() {
        List<UserDTO> users = userService.findAll();

        if (users.isEmpty()) {
            System.out.println("Users list empty!");
        } else {
            System.out.println("Users list");
            users.forEach(System.out::println);
        }
    }

    public void findBuUsername(String username) {
        try {
            UserDTO user = userService.findByUsername(username);
            System.out.println("User found: " + username);
        } catch (NotFoundException ex) {
            log.error("NotFoundException: {}", ex.getMessage());
        }

    }

    public void deleteByUsername(String username){
        try {
            userService.deleteByUsername(username);
            System.out.printf("User %s deleted!%n", username);
        } catch (NotFoundException ex) {
            log.error("NotFoundException: {}", ex.getMessage());
        }

    }

    public void create(User user){
        try {
            userService.create(user);
            System.out.printf("User with username %s created!%n", user.getUsername());
        } catch (UsernameConflictException ex) {
            log.error("UsernameConflictException: {}", ex.getMessage());
        }
    }

}
