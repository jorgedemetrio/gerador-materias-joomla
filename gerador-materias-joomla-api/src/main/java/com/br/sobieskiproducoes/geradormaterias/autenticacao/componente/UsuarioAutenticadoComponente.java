/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.autenticacao.componente;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.br.sobieskiproducoes.geradormaterias.exception.SemPermissaoException;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 2 de jul. de 2025 22:46:18
 * @version 1.0.2 de jul. de 2025
 */
@RequiredArgsConstructor
@Log
@Component
public class UsuarioAutenticadoComponente {

    private final UsuarioRepository usuarioRepository;

    public Optional<UsuarioEntity> getCurrentAuditor() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof UsuarioSistemaDTO)) {
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
