package com.EmailSystem.controller;

import com.EmailSystem.Service.UserService;
import com.EmailSystem.domain.HttpResponde;
import com.EmailSystem.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<HttpResponde> createUser(@RequestBody User user) {
        User newUser = userService.seveUser(user);
        return ResponseEntity.created(URI.create("")).body(
                HttpResponde.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("user", newUser))
                        .message("user created")
                        .status(HttpStatus.CREATED)
                        .statucCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<HttpResponde> confirmUserAccount(@RequestParam("token") String token) {
        Boolean isSuccess = userService.verifyToken(token);
        return ResponseEntity.ok().body(
                HttpResponde.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Success", isSuccess))
                        .message("Account verified")
                        .status(HttpStatus.OK)
                        .statucCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
