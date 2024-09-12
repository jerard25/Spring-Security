package com.example.security.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String Hello(HttpServletRequest request ){
        return "hello jerard" + request.getSession().getId();
    }
}
