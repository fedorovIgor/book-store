package com.example.authorize.config;

import com.example.authorize.user.service.JpaUserDetailsService;
import com.example.authorize.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityResourceConfig {

    @Autowired
    UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new JpaUserDetailsService(userRepository);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


//    @Bean
//    public SecurityContextRepository securityContextHolder() {
//        return new HttpSessionSecurityContextRepository();
//    }

//    @Bean
//    public CommandLineRunner CommandLineRunnerBean(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        return (args) -> {
//            var role = new RoleEntity();
//            role.setRoleName("user");
//            roleRepository.save(role);
//
//            var user = new UserEntity();
//            user.setUsername("user");
//            user.setPassword(passwordEncoder.encode("123"));
//            user.setRole(role);
//
//            userRepository.save(user);
//        };
//    }
}
