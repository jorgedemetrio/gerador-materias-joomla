/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.MateriaDTO;
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

  @PostMapping(path = "sugerir")
  public EntityResponse<MateriaDTO> sugerirMateria(@RequestBody @Validated final SugerirMateriaDTO request) {
    return null;
  }

}
