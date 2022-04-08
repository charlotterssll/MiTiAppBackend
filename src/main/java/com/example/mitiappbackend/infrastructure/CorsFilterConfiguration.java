package com.example.mitiappbackend.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
public class CorsFilterConfiguration {

    @Bean
    CorsWebFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // Possibly...
        // config.applyPermitDefaultValues()

        config.setAllowCredentials(true);
        config.addAllowedOrigin("https://domain1.com");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter((CorsConfigurationSource) source);
    }
}
