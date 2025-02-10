package com.wfd.tictactoe.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:4173", "http://localhost:3000", "http://localhost:4000", "http://localhost:80") // Allow several possible port
                .allowCredentials(true) // Enable the credentials
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
