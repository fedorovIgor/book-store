package com.example.authorize.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
         .allowedMethods("*")
         .allowedHeaders("*")
         .allowedOrigins("http://127.0.0.1:4200", "http://localhost:4200")
         .allowCredentials(true)
         .maxAge(-1);
    }

}

interface Cool {
    void getName(String name);
}
