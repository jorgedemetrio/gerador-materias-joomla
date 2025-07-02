/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 1 de jul. de 2025 12:31:36
 * @version 1.0.1 de jul. de 2025
 */
@Log
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Value("${application.name}")
    private String appName;

    @Value("${application.version}")
    private String version;

    @Value("${application.description}")
    private String description;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI(SpecVersion.V31).info(new Info().title(appName).version(version).description(description))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
    }
}
