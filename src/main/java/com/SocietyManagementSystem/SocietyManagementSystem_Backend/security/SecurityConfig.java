package com.SocietyManagementSystem.SocietyManagementSystem_Backend.security;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Public
                        .requestMatchers("/api/auth/login").permitAll()

                        // Auth - register only ADMIN
                        .requestMatchers("/api/auth/register").hasRole("ADMIN")

                        // Blocks
                        .requestMatchers(HttpMethod.GET, "/api/blocks/**").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT", "SECURITY_GUARD")
                        .requestMatchers("/api/blocks/**").hasAnyRole("ADMIN", "SECRETARY")

                        // Buildings
                        .requestMatchers(HttpMethod.GET, "/api/buildings/**").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT", "SECURITY_GUARD")
                        .requestMatchers("/api/buildings/**").hasAnyRole("ADMIN", "SECRETARY")

                        // Flats
                        .requestMatchers(HttpMethod.GET, "/api/flats/**").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT", "SECURITY_GUARD")
                        .requestMatchers("/api/flats/**").hasAnyRole("ADMIN", "SECRETARY")

                        // Notices
                        .requestMatchers(HttpMethod.GET, "/api/notices/**").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT", "SECURITY_GUARD")
                        .requestMatchers("/api/notices/**").hasAnyRole("ADMIN", "SECRETARY")

                        // Events
                        .requestMatchers(HttpMethod.GET, "/api/events/**").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT", "SECURITY_GUARD")
                        .requestMatchers("/api/events/**").hasAnyRole("ADMIN", "SECRETARY")

                        // Complaints
                        .requestMatchers(HttpMethod.GET, "/api/complaints/**").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT")
                        .requestMatchers(HttpMethod.POST, "/api/complaints/add").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT")
                        .requestMatchers("/api/complaints/**").hasAnyRole("ADMIN", "SECRETARY")

                        // Maintenance
                        .requestMatchers("/api/maintenance/my-bills").hasRole("RESIDENT")
                        .requestMatchers(HttpMethod.GET, "/api/maintenance/**").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT")
                        .requestMatchers("/api/maintenance/**").hasAnyRole("ADMIN", "SECRETARY")

                        // Amenity Bookings
                        .requestMatchers("/api/amenity-bookings/my-bookings").hasRole("RESIDENT")
                        .requestMatchers(HttpMethod.POST, "/api/amenity-bookings/add").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT")
                        .requestMatchers("/api/amenity-bookings/**").hasAnyRole("ADMIN", "SECRETARY")

                        // Visitors
                        .requestMatchers(HttpMethod.GET, "/api/visitors/**").hasAnyRole("ADMIN", "SECRETARY", "SECURITY_GUARD")
                        .requestMatchers("/api/visitors/**").hasAnyRole("ADMIN", "SECRETARY", "SECURITY_GUARD")

                        // Parking
                        .requestMatchers(HttpMethod.GET, "/api/parking/**").hasAnyRole("ADMIN", "SECRETARY", "SECURITY_GUARD")
                        .requestMatchers("/api/parking/**").hasAnyRole("ADMIN", "SECRETARY", "SECURITY_GUARD")

                        // Security Guards
                        .requestMatchers(HttpMethod.GET, "/api/security-guards/**").hasAnyRole("ADMIN", "SECRETARY", "SECURITY_GUARD")
                        .requestMatchers("/api/security-guards/**").hasAnyRole("ADMIN", "SECURITY_GUARD")

                        // Gym + Banquet Hall
                        .requestMatchers(HttpMethod.GET, "/api/gym/**").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT", "SECURITY_GUARD")
                        .requestMatchers("/api/gym/**").hasAnyRole("ADMIN", "SECRETARY")
                        .requestMatchers(HttpMethod.GET, "/api/banquet-hall/**").hasAnyRole("ADMIN", "SECRETARY", "RESIDENT", "SECURITY_GUARD")
                        .requestMatchers("/api/banquet-hall/**").hasAnyRole("ADMIN", "SECRETARY")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}