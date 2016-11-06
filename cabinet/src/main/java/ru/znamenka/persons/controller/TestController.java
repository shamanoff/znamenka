package ru.znamenka.persons.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @GetMapping("/me")
    public ResponseEntity me(Principal principal) {
        return ResponseEntity.ok(principal);
    }

}
