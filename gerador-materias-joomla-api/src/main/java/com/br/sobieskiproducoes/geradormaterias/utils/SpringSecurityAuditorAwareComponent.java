/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.utils;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.br.sobieskiproducoes.geradormaterias.exception.SemPermissaoException;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author JorgeDemetrioPC
 * @since 2 de jul. de 2025 00:10:24
 * @version 1.0.2 de jul. de 2025
 */
@RequiredArgsConstructor
@Primary
@Component
public class SpringSecurityAuditorAwareComponent implements AuditorAware<UsuarioEntity> {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Optional<UsuarioEntity> getCurrentAuditor() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        return usuarioRepository.findById(((UsuarioSistemaDTO) authentication.getPrincipal()).getId());

    }

    public UsuarioEntity getUsuarioLogado() {
        final Optional<UsuarioEntity> usuarioLogadoOpt = getCurrentAuditor();
        if (!usuarioLogadoOpt.isPresent()) {
            throw new SemPermissaoException("Sem permissão para acesso ao recurso.");
        }
        return usuarioLogadoOpt.get();
    }

}
