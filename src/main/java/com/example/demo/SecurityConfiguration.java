package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Esto configura las paginas que muestra o niega segun el rol
        http
                .authorizeHttpRequests
                        (
                                (requests)->
                                        requests.requestMatchers("/", "/home", "/img/**").
                                                permitAll()

                .requestMatchers(HttpMethod.GET, "/lista").hasAnyRole("Admin", "User")
                                                .requestMatchers(HttpMethod.POST, "/lista").hasAnyRole("Admin")
                                                .requestMatchers(HttpMethod.POST, "/altaUsuario", "/guardarUsuario").hasAnyRole("Admin")
                                                .anyRequest().authenticated()
                        )
                .formLogin(form -> form.loginPage("/login"));

        return http.build();
    }

    //Bcrypt para usarlo en el login de Spring
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // autentificar usuarios
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }
}
