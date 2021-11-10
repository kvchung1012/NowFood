package com.cntt2.nowfood.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 11:14 AM
 */
@Configuration
@EnableWebMvc
@NoArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Value("${endpoints.cors.allowed-methods}")
    private String[] methods;

    @Value("${localhost.path.client.user}")
    private String[] origins;

    @Value("${endpoints.cors.max-age}")
    private long maxAge;

    @Value("${endpoints.cors.allow-credentials}")
    private boolean allowCredentials;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods(methods)
                .allowCredentials(allowCredentials)
                .maxAge(maxAge);
    }
}
