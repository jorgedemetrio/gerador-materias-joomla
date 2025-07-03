/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.autenticacao.controller;

import java.util.logging.Level;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.autenticacao.dto.LoginDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.TokenSessionDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 01:09:16
 * @version 1.0.26 de jun. de 2025
 */
@Log
@RequiredArgsConstructor
@RestController
@Tag(name = "Autenticação de usuário", description = "Permite autenticar o usuário e gerar um token.")
@RequestMapping("/api/v1/autenticacao")
public class AutenticacaoController {

    private final UsuarioService service;

    @Operation(summary = "Realiza o login", description = "Retorna o token de autenticação.", responses = {
            @ApiResponse(description = "Autenticação com sucesso", responseCode = "200", content = {@Content(schema = @Schema(implementation = TokenSessionDTO.class)) }),
            @ApiResponse(description = "Dados inválidos", responseCode = "400", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "Não autorizado", responseCode = "401", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "Não permitido.", responseCode = "403", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "Erro Interno", responseCode = "500", content = { @Content(contentSchema = @Schema(implementation = ProblemDetail.class)) })
            
    }, security = { @SecurityRequirement(name = "Não seránecessároio.") })
    @PostMapping("/login")
    public ResponseEntity<TokenSessionDTO> login(@RequestBody @Valid @Validated final LoginDTO login,
            @NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId) {
        log.info(String.format("Inicio de autenticação (%s)", login.username()));
        try {
            return ResponseEntity.ok(service.login(login.username(), login.password()));
        } catch (final Exception ex) {
            log.log(Level.SEVERE, String.format("Falha na autenticação (mensagem: %s, usuario %s )", ex.getLocalizedMessage(), login.username()),
                    ex.getCause());
            // return
            // ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
            // ex.getLocalizedMessage())).build();
            throw ex;
        }
    }

}
