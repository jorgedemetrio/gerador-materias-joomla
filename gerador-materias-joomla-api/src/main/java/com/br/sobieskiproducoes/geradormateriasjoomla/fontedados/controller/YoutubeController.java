/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.controller;

import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.dto.YoutubeGerarMateriaRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.service.YoutubeService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

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

  // arquivo de segredos do cliente
  private static final Collection<String> SCOPES = Collections
      .singletonList("https://www.googleapis.com/auth/youtube.readonly");

  private  final ConfiguracoesProperties properties;

  private final YoutubeService service;

  private GoogleAuthorizationCodeFlow flow;

  @RequestMapping(path = "/callback", method = RequestMethod.GET)
  public String callback() {
    return "/fontedados/youtube/callback";
  }

  @RequestMapping(path = { "lerPlaylist" }, method = RequestMethod.POST)
  public String lerPlaylist(final Model model,
      @ModelAttribute("youtube") final YoutubeGerarMateriaRequestDTO youtubeGerarMateriaRequestDTO) throws Exception {

    model.addAttribute("materia", service.geraMateriasPlayList(youtubeGerarMateriaRequestDTO));

    return "/fontedados/youtube/materia";
  }

  @RequestMapping(path = { "lerVideo" }, method = RequestMethod.POST)
  public String lerVideo(final Model model,
      @ModelAttribute("youtube") final YoutubeGerarMateriaRequestDTO youtubeGerarMateriaRequestDTO) throws Exception {
    model.addAttribute("materias", service.geraMateria(youtubeGerarMateriaRequestDTO));
    return "/fontedados/youtube/materias";
  }

//  public ModelAndView oauth2Callback(@RequestParam(value = "code") final String code) {
//    // Aqui você implementaria o tratamento do código e a troca pelo token.
//    // Este é um exemplo simplificado.
//    return new ModelAndView("youtube");
//  }

  @RequestMapping(path = { "", "/" }, method = RequestMethod.GET)
  public String saveSeedstarter(final Model model) {
    log.info("Passou aqui");
    return "fontedados/youtube/index";
  }

  @RequestMapping("/youtubeAuth")
  public String youtubeAuth() throws Exception {
    final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(GsonFactory.getDefaultInstance(),
        new FileReader(properties.getYoutube().getArquivoSecrets()));

    flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(),
        clientSecrets, SCOPES).build();

    final String url = flow.newAuthorizationUrl().setRedirectUri(properties.getYoutube().getCallback()).build();
    return "redirect:" + url;
  }

}
