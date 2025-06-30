/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.service.ConfiguracaoService;
import com.br.sobieskiproducoes.geradormaterias.exception.DadosInvalidosException;
import com.br.sobieskiproducoes.geradormaterias.exception.NaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.exception.UsuarioNaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSistemaDTO;
import com.br.sobieskiproducoes.geradormaterias.utils.ControllerUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 29 de jun. de 2025 12:38:03
 * @version 1.0.29 de jun. de 2025
 */

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/configuracao")
public class ConfiguracaoApiController {

    private final ConfiguracaoService service;

    @GetMapping({ "/", "" })
    public ResponseEntity<ConfiguracoesDTO> get(final Authentication authentication) throws Exception {
        try {
            return ResponseEntity.ok(service.configuracaoDaEmpresaPrincipal((UsuarioSistemaDTO) authentication.getPrincipal()));
        } catch (final NaoEncontradoException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Dados da empresa não encontrado")).build();
        }
    }

    @PostMapping({ "/", "" })
    public ResponseEntity<ConfiguracoesDTO> save(@RequestBody final ConfiguracoesDTO configuracao, final Authentication authentication,
            final HttpServletRequest request) throws Exception {

        configuracao.setIpAlterador(ControllerUtils.getClientIpAddress(request));
        configuracao.setIpProxyAlterador(ControllerUtils.getClientIpProxyAddress(request));

        try {
            return ResponseEntity.ok(service.salvar(configuracao, (UsuarioSistemaDTO) authentication.getPrincipal()));
        } catch (final UsuarioNaoEncontradoException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Dados da usuario não encontrado")).build();
        } catch (final DadosInvalidosException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage())).build();
        }

    }
}
