package org.example.yardflow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SegurancaConfig {

    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests( (request) -> request
                        .requestMatchers("/login", "/login/**", "/css/**", "/img/**").permitAll()
                        .requestMatchers("/usuario/novo").hasAuthority("ADMIN")
                        .anyRequest().authenticated() )
                .formLogin( (login) -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?falha=true")
                        .permitAll())
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())
                .exceptionHandling((exception) ->
                        exception.accessDeniedHandler((request, response, ex) -> {response.sendRedirect("/login/acesso_negado");}) );

        return http.build();

    }

    @Bean
    public PasswordEncoder encoder() {
        // senha "ADMIN".
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

}
