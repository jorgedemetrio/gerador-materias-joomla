/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.consumer.YoutubePlaylistParser;
import com.br.sobieskiproducoes.geradormateriasjoomla.fontedados.dto.YoutubeGerarMateriaRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaService;

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
public class YoutubeService {

  private final YoutubePlaylistParser parser;

  private final GerarMateriaService gerarMateriaService;

  public List<PropostaMateriaDTO> geraMateria(final YoutubeGerarMateriaRequestDTO dto) throws Exception {

    return gerarMateriaService.gerarSugestaoMateria(parser.getDadosVideo(dto.getLink()));

  }

  public List<PropostaMateriaDTO> geraMateriasPlayList(final YoutubeGerarMateriaRequestDTO dto) throws Exception {
    final List<PropostaMateriaDTO> retorno = new ArrayList<>();

    parser.getDadosVideos(dto.getLink()).forEach(n -> retorno.addAll(gerarMateriaService.gerarSugestaoMateria(n)));

    log.info("Materias geradas " + retorno.size());

    return retorno;
  }

}
