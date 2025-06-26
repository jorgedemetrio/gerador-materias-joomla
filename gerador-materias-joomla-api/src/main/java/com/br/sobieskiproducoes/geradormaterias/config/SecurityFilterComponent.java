/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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
    @Override

    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

    }

    private String recovery() {
        return null;
    }

}
