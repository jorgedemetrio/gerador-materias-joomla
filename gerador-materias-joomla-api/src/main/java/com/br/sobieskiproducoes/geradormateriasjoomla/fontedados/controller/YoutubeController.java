/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/youtube")
public class YoutubeController {

  @RequestMapping(value = "/index")
  public String saveSeedstarter() {
    return "redirect:/youtube/videos";
  }

}
