/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.dto.RetornoBusinessDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.CategoriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.QuantidadeCategoriasImportadasDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/categoria")
public class CategoriaController {

  private final CategoriaService service;

  @Operation(summary = "Recarrega as categorias no banco de dados tirando do Joomla")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Processado com sucesso", content = @Content(schema = @Schema(implementation = QuantidadeCategoriasImportadasDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  @PutMapping(path = "recarregar", produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseBody
  public ResponseEntity<Map<String, Integer>> atualizar() {
    log.info("Inicio de processamento de recarga de Categoriaso Joomla");
    return ResponseEntity.ok(service.atualizarBancoCategoria());
  }

  @Operation(summary = "Retorna a lista de itens")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de Categoria", content = @Content(array = @ArraySchema(items = @Schema(implementation = RetornoBusinessDTO.class)), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
  @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseBody
  public ResponseEntity<RetornoBusinessDTO<CategoriaDTO>> find(
      @RequestParam(name = "titulo", required = false) final String titulo,
      @RequestParam(name = "p", required = false) final Integer pagina) {
    return ResponseEntity.ok(service.busca(titulo, pagina));
  }

}
