/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.empresa.dto.ConfiguracoesDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.service.ConfiguracaoService;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/api/v1/configuracao")
@Tag(name = "Controle de configurações.", description = "Controla os dados de configuração da aplicação.")
public class ConfiguracaoApiController {

    private final ConfiguracaoService service;

    @Operation(summary = "Configuração", description = "Retorna a a configuração ativa para o usuário loago.", responses = {
            @ApiResponse(description = "", responseCode = "200", content = { @Content(schema = @Schema(implementation = ConfiguracoesDTO.class)) }),
            @ApiResponse(description = "", responseCode = "404", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)), }) })
    @GetMapping({ "/", "" })
    public ResponseEntity<ConfiguracoesDTO> get(@NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId) throws Exception {

        try {
            return ResponseEntity.ok(service.configuracaoDaEmpresaPrincipal());
        } catch (final NaoEncontradoException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Dados da empresa não encontrado")).build();
        }
    }

    @Operation(summary = "Salvar Configuração", description = "Salva as configurações do usuário logado.", responses = {
            @ApiResponse(description = "", responseCode = "200", content = { @Content(schema = @Schema(implementation = ConfiguracoesDTO.class)) }),
            @ApiResponse(description = "", responseCode = "404", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }) })
    @PostMapping({ "/", "" })
    public ResponseEntity<ConfiguracoesDTO> save(@RequestBody @Validated @Valid final ConfiguracoesDTO configuracao,

            @NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId,

            final HttpServletRequest request) throws Exception {

        configuracao.setIpAlterador(ControllerUtils.getClientIpAddress(request));
        configuracao.setIpProxyAlterador(ControllerUtils.getClientIpProxyAddress(request));

        try {
            final ConfiguracoesDTO retorno = service.salvar(configuracao);

            return ResponseEntity.ok(retorno);
        } catch (final UsuarioNaoEncontradoException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Dados da usuario não encontrado")).build();
        } catch (final DadosInvalidosException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage())).build();
        }

    }
}
