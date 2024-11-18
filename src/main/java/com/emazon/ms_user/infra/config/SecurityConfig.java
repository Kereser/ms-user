package com.emazon.ms_user.infra.config;

import com.emazon.ms_user.ConsUtils;
import com.emazon.ms_user.infra.security.PasswordEncoderImpl;
import com.emazon.ms_user.infra.security.entrypoint.CustomBasicAuthenticationEntryPoint;
import com.emazon.ms_user.infra.security.entrypoint.CustomJWTEntryPoint;
import com.emazon.ms_user.infra.security.filter.JwtValidatorFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

    private static final String AUX_DEPOT_PATH = ConsUtils.builderPath().withAuxDepot().build();
    private static final String CLIENT_PATH = ConsUtils.builderPath().withClient().build();
    private static final String LOGIN_PATH = ConsUtils.builderPath().withLogin().build();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomJWTEntryPoint jwtEntryPoint) throws Exception {
        http
            .cors(cors -> cors.configurationSource(apiConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(customBasicAuthenticationEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(ConsUtils.SWAGGER_URL, ConsUtils.SWAGGER_DOCS_URL).permitAll();

                auth.requestMatchers(HttpMethod.GET, LOGIN_PATH).permitAll();
                auth.requestMatchers(HttpMethod.POST, AUX_DEPOT_PATH).hasRole(ConsUtils.ADMIN);
                auth.requestMatchers(HttpMethod.POST, CLIENT_PATH).permitAll();

                auth.anyRequest().denyAll();
            });

        http.anonymous(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new JwtValidatorFilter(jwtEntryPoint), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Profile(ConsUtils.NOT_TEST)
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsServiceImpl,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    @Profile(ConsUtils.NOT_TEST)
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderImpl().getEncoder();
    }

    CorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(ConsUtils.FRONT_URL));
        configuration.setAllowedMethods(List.of(ConsUtils.GET, ConsUtils.POST, ConsUtils.PUT, ConsUtils.DELETE));
        configuration.setAllowedHeaders(List.of(ConsUtils.AUTHORIZATION, ConsUtils.CONTENT_TYPE, ConsUtils.REQUESTED_WITH));
        configuration.setExposedHeaders(List.of(ConsUtils.AUTHORIZATION));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(ConsUtils.MATCH_ALL_URL, configuration);
        return source;
    }
}
