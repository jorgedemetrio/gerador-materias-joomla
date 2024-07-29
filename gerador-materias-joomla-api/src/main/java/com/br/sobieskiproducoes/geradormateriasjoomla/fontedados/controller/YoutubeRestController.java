/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.controller;

import java.util.logging.Level;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.dto.YoutubeGerarMateriaRoteiroRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.MateriaDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 25 de abr. de 2024 11:35:09
 * @version 1.0-25 de abr. de 2024
 */
@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/fontedados/youtube/v1")
public class YoutubeRestController {
  
  @Operation(summary = "Gerar materia baseado em um roteiro.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Processado com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  @PostMapping(path = "processar", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
      MediaType.APPLICATION_JSON_VALUE })
  @ResponseBody
  public ResponseEntity<MateriaDTO> processar(@NotNull @Validated @RequestBody final YoutubeGerarMateriaRoteiroRequestDTO request) {

    log.info("Inicio de processamento de em massa com dados: ".concat(request.toString()));

    try {
      return null;// ResponseEntity.ok(service.geraMateria(request));
    } catch (final Exception ex) {
      log.log(Level.SEVERE, ex.getMessage(), ex);
      return ResponseEntity.internalServerError().build();
    }
  }
}
