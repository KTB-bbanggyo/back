package com.panggyo.panggyo_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import com.panggyo.panggyo_backend.security.oauth2.OAuthLoginSuccessHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthLoginSuccessHandler authLoginSuccessHandler;

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .csrf(AbstractHttpConfigurer::disable)
            .oauth2Login(oauth2 -> oauth2
                .successHandler(authLoginSuccessHandler)
            )
            .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

        return http.build();
    }
}
