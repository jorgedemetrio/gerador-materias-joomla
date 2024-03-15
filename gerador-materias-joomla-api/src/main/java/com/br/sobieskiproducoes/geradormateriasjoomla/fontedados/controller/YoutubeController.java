/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.dto.YoutubeGerarMateriaRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.service.YoutubeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 4 de mar. de 2024 21:22:07
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Controller
@RequestMapping("/fontedados/youtube")
public class YoutubeController {

  private final YoutubeService service;

  public String lerPlaylist(final Model model,
      @ModelAttribute("youtube") final YoutubeGerarMateriaRequestDTO youtubeGerarMateriaRequestDTO) throws Exception {

    model.addAttribute("materia", service.geraMateria(youtubeGerarMateriaRequestDTO));

    return "/fontedados/youtube/materia";
  }

  public String lerVideo(final Model model, @ModelAttribute("youtube") final YoutubeGerarMateriaRequestDTO youtubeGerarMateriaRequestDTO   ) {
    model.addAttribute("materias", service.geraMateriasPlayList(youtubeGerarMateriaRequestDTO));
    return "/fontedados/youtube/materias";
  }

  @RequestMapping(path = { "", "/" }, method = RequestMethod.GET)
  public String saveSeedstarter(final Model model) {
    log.info("Passou aqui");
    return "fontedados/youtube/index";
  }

}
