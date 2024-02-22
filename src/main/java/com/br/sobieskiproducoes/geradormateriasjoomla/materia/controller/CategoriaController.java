/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.QuantidadeCategoriasImportadasDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 19:56:18
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/materia/categoria")
public class CategoriaController {

  private final CategoriaService service;

  @Operation(summary = "Recarrega as categorias no banco de dados tirando do Joomla")
  @PutMapping(path = "recarregar", produces = { MediaType.APPLICATION_JSON_VALUE })
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Processado com sucesso", content = @Content(oneOf = @Schema(implementation = QuantidadeCategoriasImportadasDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  public ResponseEntity<QuantidadeCategoriasImportadasDTO> atualizar() {
    log.info("Inicio de processamento de recarga de Categoriaso Joomla");
    return ResponseEntity.ok(new QuantidadeCategoriasImportadasDTO(service.atualizarBancoCategoria()));
  }

}
