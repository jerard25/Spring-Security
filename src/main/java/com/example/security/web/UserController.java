package com.example.security.web;

import com.example.security.model.Users;
import com.example.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;
@PostMapping("/save")
    public Users saveUser(@RequestBody Users user){
        return  userService.registry(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return  userService.verify(user);
    }

}
