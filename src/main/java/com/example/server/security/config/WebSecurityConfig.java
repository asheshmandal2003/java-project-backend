package com.example.server.security.config;


import com.example.server.jwt.JwtAuthFilter;
import com.example.server.user.UserRepository;
import com.example.server.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig{

    @Autowired
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationConfig authenticationConfig;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService(userRepository);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/v*/signup/**",
                                "/api/v1/signin").permitAll()
                        .anyRequest().authenticated()
                );
        http.cors(
                httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configure(http)
        );
        http.sessionManagement(
                httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS
                                )
                );
        http.authenticationProvider(authenticationConfig.authenticationProvider(userDetailsService()));
        http.addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(
                httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                                authenticationEntryPoint
                        )
        );


        return http.build();

    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration =
                new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
                "/**",
                corsConfiguration
        );
        return source;
    }

}
