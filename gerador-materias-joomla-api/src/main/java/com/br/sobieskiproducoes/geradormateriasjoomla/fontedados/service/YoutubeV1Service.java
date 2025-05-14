/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.consumer.YoutubePlaylistParser;
import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.dto.YoutubeGerarMateriaRoteiroRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.dto.YoutubeVideoDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaFonteYoutubeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 14 de mar. de 2024 21:06:23
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class YoutubeV1Service {

  private final YoutubePlaylistParser parser;

  private final GerarMateriaFonteYoutubeService gerarMateriaService;

//  public List<PropostaMateriaDTO> geraMateria(final YoutubeGerarMateriaRequestDTO dto) throws Exception {
//
//    return gerarMateriaService.gerarSugestaoMateria(parser.getDadosVideo(dto.getLink()));
//
//  }
//
//  public List<PropostaMateriaDTO> geraMateriasPlayList(final YoutubeGerarMateriaRequestDTO dto) throws Exception {
//    final List<PropostaMateriaDTO> retorno = new ArrayList<>();
//
//    List<YoutubeVideoDTO> itens = parser.getDadosVideos(dto.getLink());
//    for (YoutubeVideoDTO youtubeVideoDTO : itens) {
//      try {
//        retorno.addAll(gerarMateriaService.gerarSugestaoMateria(youtubeVideoDTO));
//      } catch (Exception e) {
//        log.log(Level.SEVERE, e.getLocalizedMessage(), e.getCause());
//        throw e;
//      }
//    }
//
//    log.info("Materias geradas " + retorno.size());
//
//    return retorno;
//  }

  public List<PropostaMateriaDTO> geraMateriasPlayList(final YoutubeGerarMateriaRoteiroRequestDTO dto) throws Exception {
    final List<PropostaMateriaDTO> retorno = new ArrayList<>();

    List<YoutubeVideoDTO> itens = parser.getDadosBasicoVideos(dto.getLink());

    for (YoutubeVideoDTO youtubeVideoDTO : itens) {
      try {
        retorno.addAll(gerarMateriaService.gerarSugestaoMateria(youtubeVideoDTO, dto));
      } catch (Exception e) {
        log.log(Level.SEVERE, e.getLocalizedMessage(), e.getCause());
        throw e;
      }
    }

    log.info("Materias geradas " + retorno.size());

    return retorno;
  }

}
