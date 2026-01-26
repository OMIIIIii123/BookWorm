/*

a) JwtAuthFilter

	Reads JWT from request

	Validates token

	Sets authenticated user in security context

b) SecurityFilterChain

	Defines security rules

	Configures JWT filter

	Enables stateless authentication

	Controls endpoint access

c) AuthenticationManager

	Used during login

	Authenticates username/password

	Returns authenticated user


 */
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.filter.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ❌ disable default login & basic auth
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                // ✅ JWT-based authorization
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/api/register/all", "/api/register").permitAll()
                .anyRequest().authenticated()
                )
                // ✅ JWT filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🔥 THIS BEAN FIXES YOUR ERROR
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
