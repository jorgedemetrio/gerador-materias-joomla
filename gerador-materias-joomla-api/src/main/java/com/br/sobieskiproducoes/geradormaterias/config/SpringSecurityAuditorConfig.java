/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.utils.SpringSecurityAuditorAwareComponent;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author JorgeDemetrioPC
 * @since 2 de jul. de 2025 00:16:58
 * @version 1.0.2 de jul. de 2025
 */
@RequiredArgsConstructor
@Configuration
public class SpringSecurityAuditorConfig {

    private final SpringSecurityAuditorAwareComponent config;

    @Bean
    public AuditorAware<UsuarioEntity> auditorProvider() {
        return config;
    }
}
