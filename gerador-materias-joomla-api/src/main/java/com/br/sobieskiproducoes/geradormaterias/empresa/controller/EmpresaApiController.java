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

import com.br.sobieskiproducoes.geradormaterias.empresa.dto.EmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.service.EmpresaService;
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
 * @since 27 de jun. de 2025 01:26:38
 * @version 1.0.27 de jun. de 2025
 */
@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/empresa")
public class EmpresaApiController {

    private final EmpresaService service;

    @GetMapping({ "/", "" })
    public ResponseEntity<EmpresaDTO> get(final Authentication authentication) throws Exception {
        try {
            return ResponseEntity.ok(service.getEmpresa((UsuarioSistemaDTO) authentication.getPrincipal()));
        } catch (final NaoEncontradoException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Dados da empresa não encontrado")).build();
        }
    }

    @PostMapping({ "/", "" })
    public ResponseEntity<EmpresaDTO> save(@RequestBody final EmpresaDTO empresa, final Authentication authentication, final HttpServletRequest request)
            throws Exception {

        empresa.setIpAlterador(ControllerUtils.getClientIpAddress(request));
        empresa.setIpProxyAlterador(ControllerUtils.getClientIpProxyAddress(request));

        try {
            return ResponseEntity.ok(service.salvar(empresa, (UsuarioSistemaDTO) authentication.getPrincipal()));
        } catch (final UsuarioNaoEncontradoException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Dados da usuario não encontrado")).build();
        } catch (final DadosInvalidosException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage())).build();
        }

    }

}
