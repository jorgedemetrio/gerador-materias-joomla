/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import java.util.List;
import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoSalvoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PublicarDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception.ObjectoJaExiteNoBancoBusinessException;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.MateriaJoomlaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@RestController
@Tag(name = "Matéiras", description = "Realiza todo processo de controle")
@RequestMapping("/gerar-materia")
public class GerarMateriaRestController {

  private final GerarMateriaService gerarMateriaService;
  private final MateriaJoomlaService service;

  @Operation(summary = "Publica um máteria que está no banco de dados no Joomla")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Processado com sucesso", content = @Content(schema = @Schema(implementation = GenericoItemJoomlaResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  @PostMapping(path = "{id}/publicar", consumes = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>>> publicar(
      @PathVariable("id") final Long id, @RequestBody final PublicarDTO dto) {
    log.info("Gerando materia sobre %d ".formatted(id));
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(service.publicarMateriaJoomla(id));
    } catch (final ObjectoJaExiteNoBancoBusinessException e) {
      log.log(Level.SEVERE, "Objecto que tentou salvar já existe", e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (final Exception e) {
      log.log(Level.SEVERE, "Erro interno", e);
    }
    return ResponseEntity.internalServerError().build();
  }

  @Operation(summary = "Carregar no banco uma sugestão de matéria baseado em um tema.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Processado com sucesso", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  @PostMapping(path = "sugerir", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
      MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<List<PropostaMateriaDTO>> sugerirMateria(@RequestBody final SugerirMateriaDTO request) throws Exception {
    log.info("Gerando materia sobre %s ".formatted(request.getTema()));
    return ResponseEntity.status(HttpStatus.CREATED).body(gerarMateriaService.gerarSugestaoMateria(request));
  }

}
