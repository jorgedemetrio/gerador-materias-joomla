/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.br.sobieskiproducoes.geradormaterias.autenticacao.componente.UsuarioAutenticadoComponente;
import com.br.sobieskiproducoes.geradormaterias.config.intercepter.SpringSecurityAuditorAwareInterceptor;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;

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

    @Bean
    public AuditorAware<UsuarioEntity> auditorProvider(final UsuarioAutenticadoComponente autenticadoComponente) {
        return new SpringSecurityAuditorAwareInterceptor(autenticadoComponente);
    }
}
