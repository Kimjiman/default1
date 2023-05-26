package com.example.default1.front.user.controller;

import com.example.default1.front.user.model.User;
import com.example.default1.front.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> createUser(User user) {
        userService.createUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
