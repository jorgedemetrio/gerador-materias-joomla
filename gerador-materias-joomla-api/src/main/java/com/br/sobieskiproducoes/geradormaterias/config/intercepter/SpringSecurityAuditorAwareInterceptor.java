/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config.intercepter;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.br.sobieskiproducoes.geradormaterias.autenticacao.componente.UsuarioAutenticadoComponente;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author JorgeDemetrioPC
 * @since 2 de jul. de 2025 00:10:24
 * @version 1.0.2 de jul. de 2025
 */
@RequiredArgsConstructor
public class SpringSecurityAuditorAwareInterceptor implements AuditorAware<UsuarioEntity> {
    final UsuarioAutenticadoComponente autenticadoComponente;

    @Override
    public Optional<UsuarioEntity> getCurrentAuditor() {

        return autenticadoComponente.getCurrentAuditor();

    }

}
