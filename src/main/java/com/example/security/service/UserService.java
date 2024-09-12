package com.example.security.service;

import com.example.security.model.Users;
import com.example.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class UserService {
    private UserRepository  userRepository;
    private BCryptPasswordEncoder bcrypt;
    private AuthenticationManager auth;
    private JwtService jwtService;

    public Users registry(Users user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
          userRepository.save(user);

          return user;

    }
    public String verify(Users user) {
        try {
            Authentication authentication = auth.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            }
        } catch (BadCredentialsException e) {
            return "Invalid credentials";
        } catch (Exception e) {
            return "Authentication failed: " + e.getMessage();
        }
        return "failure";
    }


}
