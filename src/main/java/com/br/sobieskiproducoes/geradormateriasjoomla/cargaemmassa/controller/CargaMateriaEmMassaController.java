/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller;

import java.util.List;
import java.util.logging.Level;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service.CargaMateriaEmMassaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 07:22:48
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@RestController
@Tag(name = "Carga em Massa de Materias", description = "Esses serviços disponiveis servem para fazer carga de matérias automáticas sobre o Joomla")
@RequestMapping("/carga-materia-massa")
public class CargaMateriaEmMassaController {

  private final CargaMateriaEmMassaService serveice;

  @Operation(summary = "Subir matérias novas no Joomla.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Processado com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  @PostMapping(path = "processar", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
      MediaType.APPLICATION_JSON_VALUE })
  @ResponseBody
  public ResponseEntity<List<MapaPerguntaDTO>> processar(@NotNull @Validated final RequisicaoCaragMassaDTO request) {

    log.info("Inicio de processamento de em massa com dados: ".concat(request.toString()));

    try {
      return ResponseEntity.ok(serveice.processar(request));
    } catch (final Exception ex) {
      log.log(Level.SEVERE, ex.getMessage(), ex);
      return ResponseEntity.internalServerError().build();
    }

  }

}
