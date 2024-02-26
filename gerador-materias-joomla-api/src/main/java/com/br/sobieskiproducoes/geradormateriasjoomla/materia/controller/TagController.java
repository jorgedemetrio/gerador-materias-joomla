/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import java.util.List;
import static java.util.Objects.*;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.TagDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.TagService;

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
 * @since 24 de fev. de 2024 22:45:37
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@RestController()
@Tag(name = "Tag da matéria", description = "Controller Gerenciador das Tags das Matérias ")
@RequestMapping("/tag")
public class TagController {

  private final TagService service;

  @Operation(summary = "Busca lisda de Tag filtrando por titulo.")
  @ApiResponses({ @ApiResponse(responseCode = "202", description = "Apagado com sucesso."),
      @ApiResponse(responseCode = "202", description = "Gravado com sucesso."),
      @ApiResponse(responseCode = "404", description = "Registro não encontrado.") })
  @PutMapping(path = "/{id}/update", consumes =
      { MediaType.APPLICATION_JSON_VALUE })
  @ResponseBody
  private ResponseEntity<?> atualizar(@NotNull @PathVariable(name = "id", required = false) final Long id,
      @NotNull @Validated @RequestBody final TagDTO tag) {
    final TagDTO tagBusca = service.buscarPorId(id);
    if (isNull(tagBusca)) {
      log.info("A atualizar não achou a TAG com o id:".concat(id.toString()));
      return ResponseEntity.notFound().build();
    }
    tag.setId(id);
    service.gravar(tag);
    return ResponseEntity.accepted().build();
  }

  @Operation(summary = "Busca lista de Tag filtrando por titulo.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Retorna lista de itens", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TagDTO.class)))) })
  @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseBody
  private ResponseEntity<List<TagDTO>> buscar(@RequestParam(name = "titulo", required = false) final String titulo,
      @NotNull @RequestParam(name = "p", required = false, defaultValue = "0") final Integer pagina) {
    return ResponseEntity.ok(service.buscarPorTitulo(titulo, pagina));
  }

  @Operation(summary = "Busca TAG por id.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Retorna um item ", content = @Content(schema = @Schema(implementation = TagDTO.class))),
      @ApiResponse(responseCode = "404", description = "Registro não encontrado.") })
  @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseBody
  private ResponseEntity<TagDTO> buscarItem(@NotNull @PathVariable(name = "id", required = true) final Long id) {
    final TagDTO tag = service.buscarPorId(id);
    if (isNull(tag)) {
      log.info("A busca não achou a TAG com o id:".concat(id.toString()));
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(tag);
  }


  @Operation(summary = "Apaga a Tag .")
  @ApiResponses({ @ApiResponse(responseCode = "202", description = "Apagado com sucesso."),
      @ApiResponse(responseCode = "404", description = "Registro não encontrado.") })
  @DeleteMapping("/{id}/delete")
  @ResponseBody
  private ResponseEntity<?> delete(@NotNull @PathVariable(name = "id", required = false) final Long id) {
    if (service.apagar(id)) {
      log.info("O delete não achou a TAG com o id:".concat(id.toString()));
      return ResponseEntity.accepted().build();
    }
    return ResponseEntity.notFound().build();
  }





  @Operation(summary = "Grava de Tag nova.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Gravado com sucesso.", content = @Content(schema = @Schema(implementation = TagDTO.class))) })
  @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  @ResponseBody
  private ResponseEntity<TagDTO> salvar(@NotNull @Validated @RequestBody final TagDTO tag) {

    return ResponseEntity.ok(service.gravar(tag));
  }

}
