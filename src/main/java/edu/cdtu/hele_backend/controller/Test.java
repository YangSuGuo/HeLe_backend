package edu.cdtu.hele_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class Test {
    @GetMapping("/Hello")
    public String test() {
        return "Hello World!";
    }
}
