package com.developaw.harupuppy.global.configuration;

import com.developaw.harupuppy.domain.user.OAuthSuccessHandler;
import com.developaw.harupuppy.domain.user.application.OAuthUserService;
import com.developaw.harupuppy.global.configuration.filter.AuthenticationFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class AuthenticationConfig {
    private final OAuthUserService oAuth2UserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final AuthenticationFilter authenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/api/users/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트엔드 주소
                            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            corsConfiguration.setAllowedHeaders(List.of("*"));
                            corsConfiguration.setAllowCredentials(true);
                            corsConfiguration.setMaxAge(3600L);
                            return corsConfiguration;
                        }))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        (sessionManagement) ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/api/users/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                        .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig
                                .baseUri("/auth/callback/"))
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(oAuth2UserService))
                        .successHandler(oAuthSuccessHandler)
                )
                .addFilterBefore(authenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
