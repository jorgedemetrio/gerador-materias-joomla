/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.GrupoEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.PermissaoEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.model.UsuarioEntity;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.GrupoRepository;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.PermissaoRepository;
import com.br.sobieskiproducoes.geradormaterias.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 14 de jun. de 2025 02:19:39
 */
@Service
@Log
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarios;

    private final GrupoRepository grupos;

    private final PermissaoRepository permissoes;

    private Collection<? extends GrantedAuthority> authorities(final List<GrupoEntity> grupos) {
        final Collection<GrantedAuthority> auths = new ArrayList<>();

        for (final GrupoEntity grupo : grupos) {
            final List<PermissaoEntity> lista = permissoes.findByGruposIn(grupo.getId().toString());

            for (final PermissaoEntity permissao : lista) {
                auths.add(new SimpleGrantedAuthority(permissao.getNome()));
            }
        }

        return auths;
    }

    private Collection<? extends GrantedAuthority> authorities(final UsuarioEntity usuario) {
        return authorities(grupos.findByUsuariosIdIn(usuario.getId().toString()));
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UsuarioEntity usuario = usuarios.findByUsuarioIgnoreCase(username.trim());

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        return new UsuarioSistemaDTO(usuario.getNome(), usuario.getUsuario(), usuario.getSenha(), usuario.getHabilitado(), usuario.getExpira(),
                authorities(usuario));
    }

}
