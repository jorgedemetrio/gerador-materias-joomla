/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import static java.util.Objects.isNull;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.MateriaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 7 de abr. de 2024 22:01:58
 * @version 1.0-7 de abr. de 2024
 */
@Log
@RequiredArgsConstructor
@Controller
@Tag(name = "Matérias", description = "Realiza todo processo de controle")
@RequestMapping("/materia")
public class MateriaController {

  MateriaService service;

  @GetMapping(params = { "", "/" })
  public String get(Model model, @RequestAttribute(name = "p") final Integer pagina, final String order) {
    
    model.addAttribute("itens", service.buscarMateria(isNull(pagina) ? 0 : pagina, order));
    

    return "/index";
  }

}
