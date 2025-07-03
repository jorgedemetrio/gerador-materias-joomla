/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.empresa.dto.EmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.service.EmpresaService;
import com.br.sobieskiproducoes.geradormaterias.exception.DadosInvalidosException;
import com.br.sobieskiproducoes.geradormaterias.exception.NaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.exception.UsuarioNaoEncontradoException;
import com.br.sobieskiproducoes.geradormaterias.utils.ControllerUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
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
@Tag(name = "Controle de Empresas.", description = "Controla os dados da empresa.")
public class EmpresaApiController {

    private final EmpresaService service;

    @Operation(summary = "Consulta os dados da empresa do usuário autenticado.", description = "Consulta os dados da empresa do usuário autenticado.", responses = {
            @ApiResponse(description = "", responseCode = "200", content = { @Content(schema = @Schema(implementation = EmpresaDTO.class)) }),
            @ApiResponse(description = "", responseCode = "400", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "401", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "403", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "500", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }) })
    @GetMapping({ "" })
    public ResponseEntity<EmpresaDTO> get(@NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId) throws Exception {
        try {
            return ResponseEntity.ok(service.getEmpresa());
        } catch (final NaoEncontradoException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Dados da empresa não encontrado")).build();
        }
    }

    @Operation(summary = "Alterar os dados da empresa do usuário autenticado.", description = "Alterar os dados da empresa do usuário autenticado.", responses = {
            @ApiResponse(description = "", responseCode = "200", content = { @Content(schema = @Schema(implementation = EmpresaDTO.class)) }),
            @ApiResponse(description = "", responseCode = "400", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "401", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "403", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "500", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }) })
    @PostMapping({ "" })
    public ResponseEntity<EmpresaDTO> save(@RequestBody final EmpresaDTO empresa,

            @NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId,

            final HttpServletRequest request) throws Exception {

        empresa.setIpAlterador(ControllerUtils.getClientIpAddress(request));
        empresa.setIpProxyAlterador(ControllerUtils.getClientIpProxyAddress(request));

        try {
            return ResponseEntity.ok(service.salvar(empresa));
        } catch (final UsuarioNaoEncontradoException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Dados da usuario não encontrado")).build();
        } catch (final DadosInvalidosException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage())).build();
        }

    }

}
