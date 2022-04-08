package com.fedorovigor.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("OPTIONS", "GET", "HEAD", "POST", "PUT"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .sessionFixation().migrateSession();


        http.httpBasic();

        http.cors();

        http.csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .mvcMatchers(HttpMethod.GET, "**/login").permitAll()
                .mvcMatchers(HttpMethod.POST, "**/user/create").permitAll()
                .mvcMatchers(HttpMethod.PUT, "**/user/password").authenticated()
                .mvcMatchers(HttpMethod.GET, "/api/v1/user/**").hasAuthority("get_users")
                .mvcMatchers(HttpMethod.PUT, "/api/v1/user/**").hasAuthority("modificate_users")

                .mvcMatchers(HttpMethod.GET, "/api/v1/role/**").hasAuthority("get_roles")
                .mvcMatchers(HttpMethod.POST, "/api/v1/role/**").hasAuthority("create_role")
                .mvcMatchers(HttpMethod.PUT, "/api/v1/role/**").hasAuthority("modificate_roles")

                .mvcMatchers("/swagger-ui").hasAuthority("get_logs")
                .mvcMatchers("/v3/api-docs").hasAuthority("get_logs")

                .mvcMatchers(HttpMethod.POST, "api/v1/bucket/**").hasAuthority("create_book")
                .mvcMatchers(HttpMethod.PUT, "api/v1/bucket/**").hasAuthority("update_book")
                .mvcMatchers(HttpMethod.GET, "api/v1/bucket/**").permitAll()

                .anyRequest().permitAll();

    }
}
