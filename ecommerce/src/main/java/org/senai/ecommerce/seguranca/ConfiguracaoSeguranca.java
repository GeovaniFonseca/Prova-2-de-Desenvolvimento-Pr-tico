package org.senai.ecommerce.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {

    @Autowired
    SegurancaFiltro segurancaFiltro;

    @Bean
    public SecurityFilterChain filtroSeguranca(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/swagger-resources/*",
                                        "/v3/api-docs/**"
                                ).permitAll()
                                .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/autenticacao/registrar").permitAll()
                                .requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/produtos").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/produtos").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(segurancaFiltro, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
