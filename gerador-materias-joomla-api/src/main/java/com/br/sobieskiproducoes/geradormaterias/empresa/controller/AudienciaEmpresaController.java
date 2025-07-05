/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.empresa.controller;

import java.util.List;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormaterias.empresa.dto.AudienciaEmpresaDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.service.AudienciaService;
import com.br.sobieskiproducoes.geradormaterias.utils.ControllerUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author JorgeDemetrioPC
 * @since 4 de jul. de 2025 01:17:39
 * @version 1.0.4 de jul. de 2025
 */
@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/audiencia-empresa")
@Tag(name = "Controle de Perfil de audiência  da Empresa.", description = "Dores da audiência que a empresa quer trabalhar.")
public class AudienciaEmpresaController {

    private final AudienciaService service;

    @Operation(summary = "Consulta de audiências disponiveis para aquela empresa.", description = "Retorna uma lista audiências.", responses = {
            @ApiResponse(description = "", responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = AudienciaEmpresaDTO.class))) }),
            @ApiResponse(description = "", responseCode = "400", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "401", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "403", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "500", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }) })
    @GetMapping({ "" })
    public ResponseEntity<List<AudienciaEmpresaDTO>> get(@NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId,
            @NotBlank @RequestHeader(name = "empresa", required = true) final String empresa,

            @RequestParam(name = "nome", required = false) final String nome,
            @RequestParam(name = "pagina", required = false, defaultValue = "0") final Integer pagina,
            @RequestParam(name = "pagina", required = false, defaultValue = "20") @Min(2) final Integer itensPorPagina,
            @RequestParam(name = "ordenarPor", required = false, defaultValue = "nome") final String ordenarPor) throws Exception {
        return ResponseEntity.ok(service.consultar(nome, empresa, pagina, itensPorPagina, ordenarPor));
    }

    @Operation(summary = "Consulta uma audiência especifico.", description = "Retorna uma audiência.", responses = {
            @ApiResponse(description = "", responseCode = "200", content = { @Content(schema = @Schema(implementation = AudienciaEmpresaDTO.class)) }),
            @ApiResponse(description = "", responseCode = "404", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "400", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "401", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "403", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "500", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }) })
    @GetMapping({ "/{id}" })
    public ResponseEntity<AudienciaEmpresaDTO> getPorId(@NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId,
            @NotBlank @RequestHeader(name = "empresa", required = true) final String empresa,

            @PathVariable(name = "id", required = false) final String id) throws Exception {

        return ResponseEntity.ok(service.item(id, empresa));

    }

    @Operation(summary = "Apaga a audiência por um id.", description = "Não retorna nada.", responses = {
            @ApiResponse(description = "", responseCode = "204", content = { @Content(schema = @Schema(hidden = true)) }),
            @ApiResponse(description = "", responseCode = "404", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "400", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "401", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "403", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "500", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }) })
    @DeleteMapping({ "/{id}" })
    public ResponseEntity<Void> apagar(@NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId,
            @NotBlank @RequestHeader(name = "empresa", required = true) final String empresa,

            @PathVariable(name = "id", required = false) final String id) throws Exception {

        service.delete(id, empresa);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Grava a audiência.", description = "Retorna a audiência gravada.", responses = {
            @ApiResponse(description = "", responseCode = "204", content = { @Content(schema = @Schema(implementation = AudienciaEmpresaDTO.class)) }),
            @ApiResponse(description = "", responseCode = "404", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "400", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "401", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "403", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(description = "", responseCode = "500", content = { @Content(schema = @Schema(implementation = ProblemDetail.class)) }) },

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = AudienciaEmpresaDTO.class))))
    @PostMapping({ "" })
    public ResponseEntity<AudienciaEmpresaDTO> gravar(@NotBlank @RequestHeader(name = "X-Tracking-ID", required = true) final String trakingId,
            @NotBlank @RequestHeader(name = "X-Session-ID", required = true) final String sessionId,
            @NotBlank @RequestHeader(name = "empresa", required = true) final String empresa,

            @RequestBody @NotNull @Valid @Validated final AudienciaEmpresaDTO item, final HttpServletRequest request) throws Exception {
        item.setIpAlterador(ControllerUtils.getClientIpAddress(request));
        item.setIpProxyAlterador(ControllerUtils.getClientIpProxyAddress(request));
        return ResponseEntity.ok(service.gravar(item, empresa));
    }
}
