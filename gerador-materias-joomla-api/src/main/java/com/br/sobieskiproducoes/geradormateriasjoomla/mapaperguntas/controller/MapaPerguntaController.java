/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.RequisitaPerguntasDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.GerarMapaPerguntasService;

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
 * @since 24 de fev. de 2024 12:43:03
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@RestController
@Tag(name = "Mapa mental de perguntas", description = "Controller Gerenciador de mapas de perguntas  para sugerir matérias")
@RequestMapping("/mapaperguntas")
public class MapaPerguntaController {

  private final GerarMapaPerguntasService gerarService;

  @Operation(summary = "Gerar um mapa de pergunta para poder criar maerias de forma mais tranguila")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de Mapas mentas de possiveis perguntas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MapaPerguntaDTO.class)), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  @PostMapping(path = "gerar", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
      MediaType.APPLICATION_JSON_VALUE })
  @ResponseBody
  public ResponseEntity<List<MapaPerguntaDTO>> gerar(@NotNull @Validated @RequestBody final RequisitaPerguntasDTO request) throws Exception {
    log.info("Gerando perguntas para o site.");
    return ResponseEntity.ok(gerarService.gerarMapa(request));
  }
}
