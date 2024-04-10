package com.example.shopwatchbackend.configuration;

import com.example.shopwatchbackend.component.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests(requests -> {
                        requests
                                .requestMatchers(HttpMethod.POST,endpoints.PUBLIC_POST).permitAll()
                                .requestMatchers(HttpMethod.GET,endpoints.PUBLIC_GET).permitAll()
                                .anyRequest().authenticated();
                    });
        httpSecurity.cors(cors -> {
            cors.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
                configuration.setAllowedHeaders(List.of("*"));
                return configuration;
            });
        });
        return httpSecurity.build();
    }
}
