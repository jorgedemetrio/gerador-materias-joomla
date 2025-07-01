/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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

    @Value("${build.version}")
    private String version;

    @Value("${application.description}")
    private String description;

    public OpenAPI openApi() {
        return new OpenAPI().info(new Info().title(appName).version(version).description(description));
    }
}
