package com.github.martmatix.pproproject.configurations;

import com.github.martmatix.pproproject.custom_authorities.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/register", "/register/validateRegistration", "/login").anonymous()
                                .requestMatchers("/admin", "/admin/approve**", "/admin/revoke**", "/admin/elevate**", "/admin/degrade**",
                                        "/announcements/delete**", "/announcements/add",
                                        "/admin/completeTicket/", "/admin/createTicket", "/admin/removeTicket/", "/admin/reopenTicket/").hasAuthority(Role.ADMINISTRATOR.getValue())
                                .anyRequest().authenticated()
                ).csrf(AbstractHttpConfigurer::disable)
                .formLogin(login -> login.loginPage("/login")
                        .defaultSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}