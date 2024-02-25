/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.MateriaConstants;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.CargaDadosImagensProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.GerarMapaPerguntasService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.utils.SugerirMateriaUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 25 de fev. de 2024 09:44:23
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class CargaMateriaEmMassaService {

  private final GerarMapaPerguntasService gerarMapaPerguntasService;
  private final GerarMateriaService gerarMateriaService;
  private final CargaDadosImagensProperties properties;

  public List<MapaPerguntaDTO> processar(final RequisicaoCaragMassaDTO request) {

    log.info("Inicio processamento ".concat(LocalDateTime.now().toString()));
    final String uuid = UUID.randomUUID().toString();
    final List<MapaPerguntaDTO> itens = gerarMapaPerguntasService.gerarMapa(request.getIdeias(), uuid);

    new Thread(new ProcessoLote(properties, gerarMateriaService, uuid, request, itens)).start();

    log.info("Concluído processamento dos mapas e vai iniciar os da materias em lote"
        .concat(LocalDateTime.now().toString()));
    return itens;
  }

}

@Log
@RequiredArgsConstructor
class ProcessoLote implements Runnable {
  private final CargaDadosImagensProperties properties;
  private final GerarMateriaService gerarMateriaService;
  private final String uuid;
  private final RequisicaoCaragMassaDTO request;
  private final List<MapaPerguntaDTO> itens;

  private List<String> getTermos(final MapaPerguntaDTO dto) {
    final List<String> itens = new ArrayList<>(dto.getPerguntasAlternativas());
    itens.add(dto.getPergunta());
    return itens;
  }

  /**
   * @param data
   * @param dataPublicadas
   * @param mapaPerguntaDTO
   */
  private void processarMateria(final LocalDateTime data, final Set<String> dataPublicadas,
      final MapaPerguntaDTO mapaPerguntaDTO) {
    if (!dataPublicadas.contains(data.format(MateriaConstants.DATETIME_FORMATTER))) {

      final List<PropostaMateriaDTO> maerias = gerarMateriaService
          .gerarSugestaoMateria(new SugerirMateriaDTO(uuid, getTermos(mapaPerguntaDTO), data,
          mapaPerguntaDTO.getCategoria().getId(), request.getIdeias().getAudiencias()));


      dataPublicadas.add(data.format(MateriaConstants.DATETIME_FORMATTER));
      itens.remove(mapaPerguntaDTO);
    }
  }

  @Override
  public void run() {
    LocalDateTime data = null;
    final Set<String> dataPublicadas = new HashSet<>();
    for (final String hora : request.getHoarios()) {

      // Primeiro processa os com data
      final List<MapaPerguntaDTO> processarComData = itens.stream().filter(n -> nonNull(n.getDataSugestaoPublicacao()))
          .collect(Collectors.toList());
      for (final MapaPerguntaDTO mapaPerguntaDTO : processarComData) {

        data = SugerirMateriaUtils.getLocalDateTime(mapaPerguntaDTO.getDataSugestaoPublicacao(), hora);

        processarMateria(data, dataPublicadas, mapaPerguntaDTO);
      }
    }
    for (final String hora : request.getHoarios()) {
      final long rodar = nonNull(request.getDataFimPublicacao())
          ? SugerirMateriaUtils.getDaysBetween(request.getDataInicioPublicacao(), request.getDataFimPublicacao())
          : 1L;
      long rodaram = 0;
      while ((rodaram++) <= rodar && itens.size() > 0) {
        final MapaPerguntaDTO mapaPerguntaDTO = itens.get(0);
        data = SugerirMateriaUtils.getLocalDateTime(mapaPerguntaDTO.getDataSugestaoPublicacao(), hora);
        processarMateria(data, dataPublicadas, mapaPerguntaDTO);
      }
    }
  }

}