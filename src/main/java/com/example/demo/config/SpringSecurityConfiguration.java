package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // только ADMIN
                        .requestMatchers("/employee/**").hasAnyRole("USER", "ADMIN")  // USER или ADMIN
                        .requestMatchers("/public/**").permitAll()  // доступ всем
                        .anyRequest().authenticated()  // остальные — только аутентифицированные
                )

                .formLogin(form -> form
                .loginPage("/login")  // URL вашей кастомной формы
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                    if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                        response.sendRedirect("/admin/dashboard");
                    } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("EMPLOYEE"))) {
                        response.sendRedirect("/employee/lk");
                    } else {
                        response.sendRedirect("/default");
                            }
                        })
        )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
