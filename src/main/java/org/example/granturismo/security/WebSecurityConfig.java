package org.example.granturismo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity //Importante para anotaciones @PreAuthorize
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // *** MODIFICACIÓN AQUÍ: AÑADIR /users/verificar Y USAR antMatcher CONSISTENTEMENTE ***
                        .requestMatchers(antMatcher(HttpMethod.POST, "/users/login")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/users/register")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/users/verificar")).permitAll() // <-- Asegura que verificar también sea público
                        // *** FIN MODIFICACIÓN ***

                        // Otras rutas públicas que ya tenías:
                        .requestMatchers(antMatcher("/mail/**")).permitAll()
                        .requestMatchers(antMatcher("/doc/**")).permitAll() // Swagger docs
                        .requestMatchers(antMatcher("/v3/**")).permitAll() // Swagger v3 api-docs

                        .anyRequest().authenticated() // Cualquier otra petición requiere autenticación
                )
                .formLogin(AbstractHttpConfigurer::disable) // Deshabilita form login
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint)); // Manejador de errores de autenticación

        // Configuración de sesión sin estado (típico con JWT)
        // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // <-- Considera añadir esto si usas JWT stateless


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
