/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.dto.RetornoBusinessDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.CategoriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.CategoriaService;

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
 * @since 21 de fev. de 2024 19:56:18
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@RestController()
@Tag(name = "Categoria", description = "Controller Gerenciador das Categorias das Matérias ")
@RequestMapping("/categoria")
public class CategoriaController {

	private CategoriaService service;

	@Operation(summary = "Recarrega as categorias no banco de dados tirando do Joomla")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Processado com sucesso") })
	@RequestMapping(method = RequestMethod.PATCH, path = "recarregar", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<Map<String, Integer>> atualizar() {
		log.info("Inicio de processamento de recarga de Categoriaso Joomla");
		return ResponseEntity.ok(service.atualizarBancoCategoria());
	}

	@Operation(summary = "Retorna a lista de itens")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Lista de Categoria", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RetornoBusinessDTO.class)), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<RetornoBusinessDTO<CategoriaDTO>> find(
			@RequestParam(name = "titulo", required = false) final String titulo,
			@RequestParam(name = "p", required = false, defaultValue = "0") final Integer pagina) {
		return ResponseEntity.ok(service.busca(titulo, pagina));
	}

	@Operation(summary = "busca categoria por id")
	@ApiResponse(responseCode = "200", description = "retorna uma categoria", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaDTO.class))))
	@ApiResponse(responseCode = "404", description = "categoria não encontrada")
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<CategoriaDTO> buscaPorId(@NotNull @PathVariable(name = "id", required = true) final Long id) {
		var categoria = service.findById(id);
		if (Objects.nonNull(categoria)) {
			return ResponseEntity.ok(categoria);
		}
		return ResponseEntity.notFound().build();
	}

	@Operation(summary = "busca categoria por titulo")
	@ApiResponse(responseCode = "200", description = "retorna categoria", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaDTO.class))))
	@ApiResponse(responseCode = "404", description = "categoria não encontrada")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody()
	private ResponseEntity<RetornoBusinessDTO<CategoriaDTO>> buscar(
			@RequestParam(name = "titulo", required = false) final String titulo,
			@NotNull @RequestParam(name = "p", required = false, defaultValue = "0") final Integer pagina) {
		return ResponseEntity.ok(service.busca(titulo, pagina));
	}

	@Operation(summary = "apaga a categoria")
	@ApiResponse(responseCode = "200", description = "categoria apagada com sucesso")
	@ApiResponse(responseCode = "404", description = "erro ao apagar categoria")
	@DeleteMapping("/{id}/delete")
	@ResponseBody
	private ResponseEntity<?> delete(@NotNull @PathVariable(name = "id", required = false) final Long id) {
		if (service.apagar(id)) {
			log.info("Não encontrada categoria com o id:".concat(id.toString()));
			return ResponseEntity.accepted().build();
		}
		return ResponseEntity.notFound().build();
	}

	@Operation(summary = "gravar categoria")
	@ApiResponse(responseCode = "200", description = "categoria salva com sucesso.", content = @Content(schema = @Schema(implementation = CategoriaDTO.class)))
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	ResponseEntity<CategoriaDTO> salvar(@NotNull @Validated @RequestBody final CategoriaDTO categoria) {

		return ResponseEntity.ok(service.gravar(categoria));
	}

}
