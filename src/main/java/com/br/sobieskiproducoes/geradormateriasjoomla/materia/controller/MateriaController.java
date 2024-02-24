/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PublilcarDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.MateriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/materia")
public class MateriaController {

  private final MateriaService service;

  @Operation(summary = "Publica um máteria que está no banco de dados no Joomla")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Processado com sucesso", content = @Content(oneOf = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  @PostMapping(path = "{id}/publicar")
  @ResponseBody
  public ResponseEntity<String> publicar(@PathVariable("id") final Long id, @RequestBody final PublilcarDTO dto) {
    log.info("Gerando materia sobre %d ".formatted(id));
    return ResponseEntity.status(HttpStatus.CREATED).body(service.publicarMateriaJoomla(id, dto.getDataPublicacao()));
  }

  @Operation(summary = "Carregar no banco uma sugestão de matéria baseado em um tema.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Processado com sucesso", content = @Content(oneOf = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  @PostMapping(path = "sugerir")
  @ResponseBody
  public ResponseEntity<List<PropostaMateriaDTO>> sugerirMateria(@RequestBody final SugerirMateriaDTO request) {
    log.info("Gerando materia sobre %s ".formatted(request.getTema()));
    return ResponseEntity.status(HttpStatus.CREATED).body(service.sugerirMateria(request));
  }

}
