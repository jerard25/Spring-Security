package com.example.security.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;
    private  JwtFilter jwtFilter;
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 return  http
          .csrf(customer-> customer.disable())
         .authorizeHttpRequests(request-> request
                 .requestMatchers("save", "login")
                 .permitAll()
                 .anyRequest().authenticated())
    // http.formLogin(Customizer.withDefaults());
           .httpBasic(Customizer.withDefaults())
        .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
         provider.setUserDetailsService(userDetailsService);
        return provider;

    }
    /*@Bean
    public UserDetailsService userDetailsService (){
        UserDetails user1= User
                .withDefaultPasswordEncoder()
                .username("jerard")
                .password("1234")
                .roles("user")
                .build();
        UserDetails user2= User
                .withDefaultPasswordEncoder()
                .username("jerar")
                .password("1234")
                .roles("admin")
                .build();


    return  new InMemoryUserDetailsManager( user1,user2);
    }*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
       return config.getAuthenticationManager();
    }
}
