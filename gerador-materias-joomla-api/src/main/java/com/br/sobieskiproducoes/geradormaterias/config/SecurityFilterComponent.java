/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.br.sobieskiproducoes.geradormaterias.usuario.service.TokenSerice;
import com.br.sobieskiproducoes.geradormaterias.usuario.service.UsuarioDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 02:20:04
 * @version 1.0.26 de jun. de 2025
 */
@Log
@Component
@RequiredArgsConstructor
public class SecurityFilterComponent extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";

    private final TokenSerice service;

    private final UsuarioDetailsService usuarioService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        final String auth = recovery(request);
        if (nonNull(auth)) {
            final String username = service.validToken(auth);
            if (nonNull(username)) {
                final UserDetails usuario = usuarioService.loadUserByUsername(username);

                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));

            }
        }
        filterChain.doFilter(request, response);
    }

    private String recovery(final HttpServletRequest request) {
        final String auth = request.getHeader(AUTHORIZATION);

        if (isNull(auth) || auth.isBlank()) {
            return null;
        }

        return auth.replace("Bearer ", "");
    }

}
