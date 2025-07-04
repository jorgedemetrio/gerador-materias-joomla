/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.usuario.controller;

import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioRequestDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.dto.UsuarioSimplificadoDTO;
import com.br.sobieskiproducoes.geradormaterias.usuario.exception.UsuarioExistenteException;
import com.br.sobieskiproducoes.geradormaterias.usuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 26 de jun. de 2025 21:01:11
 * @version 1.0.26 de jun. de 2025
 */
@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuario")
@Tag(name = "Controle de usuários do sistema.", description = "Controla os usuários do sistema.")
public class UsuarioAPIController {

    private final UsuarioService service;

    @Operation(summary = "Grava o usuário.", description = "O usuário será gravado.", responses = {
            @ApiResponse(description = "", responseCode = "200", content = { @Content(schema = @Schema(implementation = UsuarioSimplificadoDTO.class)) }),
            @ApiResponse(description = "", responseCode = "404", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "400", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "401", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "403", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "500", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }) },

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = UsuarioRequestDTO.class))))
    @PostMapping({ "" })
    public ResponseEntity<UsuarioSimplificadoDTO> salvar(@NotNull @Valid @Validated @RequestBody final UsuarioRequestDTO item,
            @NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId,
            @NotBlank @RequestHeader(name = "empresa", required = true) final String empresa, final HttpServletRequest request) throws Exception {
        try {

//            item.setIpAlterador(ControllerUtils.getClientIpAddress(request));
//            item.setIpProxyAlterador(ControllerUtils.getClientIpProxyAddress(request));
            return ResponseEntity.ok(service.salvar(item));
        } catch (final UsuarioExistenteException ex) {
            log.log(Level.WARNING, String.format("Usuário existente : ", item.getUsuario()));
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage())).build();
        }
    }

}
