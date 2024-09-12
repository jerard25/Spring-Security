package com.example.security.service;

import com.example.security.model.UserPrincipal;
import com.example.security.model.Users;
import com.example.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class MyUserDetailsService  implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user= userRepository.findByUsername( username);
        if (user== null) throw  new  UsernameNotFoundException("user not found");
        return new UserPrincipal(user);
    }
}
