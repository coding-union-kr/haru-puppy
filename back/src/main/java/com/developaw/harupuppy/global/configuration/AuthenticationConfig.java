package com.developaw.harupuppy.global.configuration;

import com.developaw.harupuppy.domain.user.application.UserService;
import com.developaw.harupuppy.global.configuration.filter.AuthenticationFilter;
import com.developaw.harupuppy.global.utils.JwtTokenUtils;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
@Configuration
public class AuthenticationConfig {
    private final CorsConfigurationSource corsConfigurationSource;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(CorsConfigurer -> CorsConfigurer.configurationSource(corsConfigurationSource))
                .csrf(CsrfConfigurer -> CsrfConfigurer.disable())
                .sessionManagement(
                        (sessionManagement) ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/api/users/**", "/auth/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(new AuthenticationFilter(userService, jwtTokenUtils),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
        config.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "PATCH", "DELETE", "PUT", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
