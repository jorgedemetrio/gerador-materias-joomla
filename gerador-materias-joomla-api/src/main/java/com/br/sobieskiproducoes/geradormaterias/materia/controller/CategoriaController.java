/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.controller;

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

import com.br.sobieskiproducoes.geradormaterias.dto.RetornoBusinessDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.CategoriaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.service.CategoriaService;

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
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService service;

    @RequestMapping(method = RequestMethod.PATCH, path = "recarregar", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> atualizar() {
        log.info("Inicio de processamento de recarga de Categoriaso Joomla");
        return null;// TODO: ARRUMAR AQUI ResponseEntity.ok(service.atualizarBancoCategoria());
    }

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<CategoriaDTO> buscaPorId(@NotNull @PathVariable(name = "id", required = true) final Long id) {
        final var categoria = service.findById(id);
        if (Objects.nonNull(categoria)) {
            return ResponseEntity.ok(categoria);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody()
    private ResponseEntity<RetornoBusinessDTO<CategoriaDTO>> buscar(@RequestParam(name = "titulo", required = false) final String titulo,
            @NotNull @RequestParam(name = "p", required = false, defaultValue = "0") final Integer pagina) {
        return ResponseEntity.ok(service.busca(titulo, pagina));
    }

    @DeleteMapping("/{id}/delete")
    @ResponseBody
    private ResponseEntity<?> delete(@NotNull @PathVariable(name = "id", required = false) final Long id) {
        if (service.apagar(id)) {
            log.info("Não encontrada categoria com o id:".concat(id.toString()));
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    ResponseEntity<CategoriaDTO> salvar(@NotNull @Validated @RequestBody final CategoriaDTO categoria) {

        return ResponseEntity.ok(service.gravar(categoria));
    }

}
