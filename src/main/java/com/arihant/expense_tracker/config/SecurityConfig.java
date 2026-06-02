package com.arihant.expense_tracker.config;

import com.arihant.expense_tracker.filters.JwtFilter;
import com.arihant.expense_tracker.service.UserDetailsServiceImplmt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private UserDetailsServiceImplmt userDetailsService;
    private JwtFilter jwtFilter;

    private final String[] PUBLIC_URLs = {
            "/test/**",
            "/user/register",
            "/user/login",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v2/api-docs/**"
    };

    public SecurityConfig(UserDetailsServiceImplmt userDetailsService,JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Argon2PasswordEncoder(16,32,1,65536,3);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(csrfConfObj -> csrfConfObj.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLs).permitAll()
                        .requestMatchers("/user/get-all-users").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }
}
