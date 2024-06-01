package com.pocketplanner.security;

import com.pocketplanner.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**"))
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(new AntPathRequestMatcher("/user", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/user/*", "GET")).hasAnyRole("ADMIN", "USER")
                                .requestMatchers(new AntPathRequestMatcher("/user", "POST")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/user", "PUT")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/user/*", "DELETE")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/goal", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/goal/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/goal/userId/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/goal/*", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/goal/*", "DELETE")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/goal/*", "PUT")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/transaction", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/transaction/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/transaction/userId/*")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/transaction/*", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/transaction/*", "DELETE")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/account", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/account/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/account/userId/*", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/account/*", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/account/*", "DELETE")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/security/registration", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/security/token", "POST")).permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
