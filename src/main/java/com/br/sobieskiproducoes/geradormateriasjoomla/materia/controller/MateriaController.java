/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PublilcarDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.MateriaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@RestController
@RequestMapping("/materia")
public class MateriaController {

  private final MateriaService service;

  @PostMapping(path = "{id}/publicar")
  public ResponseEntity<String> publicar(@PathVariable("id") final Long id, @RequestBody final PublilcarDTO dto) {
    log.info("Gerando materia sobre %d ".formatted(id));
    return ResponseEntity.status(HttpStatus.CREATED).body(service.materiaJoomla(id, dto.getDataPublicacao()));
  }

  @PostMapping(path = "sugerir")
  public ResponseEntity<List<PropostaMateriaDTO>> sugerirMateria(
      @RequestBody @Validated final SugerirMateriaDTO request) {
    log.info("Gerando materia sobre %s ".formatted(request.getTema()));
    return ResponseEntity.status(HttpStatus.CREATED).body(service.sugerirMateria(request));
  }

}
