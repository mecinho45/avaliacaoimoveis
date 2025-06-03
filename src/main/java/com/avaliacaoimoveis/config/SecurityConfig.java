package com.avaliacaoimoveis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Endpoints sem contexto
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/api/ceps/**").permitAll()
                        // Endpoints com contexto "/avaliacao-imoveis"
                        .requestMatchers("/avaliacao-imoveis/login").permitAll()
                        .requestMatchers("/avaliacao-imoveis/css/**", "/avaliacao-imoveis/js/**").permitAll()
                        .requestMatchers("/avaliacao-imoveis/api/ceps/**").permitAll()
                        .requestMatchers("/avaliacao-imoveis/resultado.html", "/resultado.html").permitAll()
                        .requestMatchers("/avaliacao-imoveis/resultado/**", "/resultado/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/index", true) // Redireciona para /index após login
                        .permitAll()
                )
                .logout(logout -> logout
                        // Redireciona para a página padrão de login após logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Desabilitar CSRF temporariamente para resolver o problema de POST
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Removido o redirecionamento para evitar loop
        // registry.addRedirectViewController("/avaliacao-imoveis/", "/login");
    }
}
