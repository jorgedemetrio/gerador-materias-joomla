/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.model.CargaMassaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.model.StatusCargaEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.repository.CargaMassaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.GerarMapaPerguntasService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.MateriaJoomlaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.utils.SugerirMateriaUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
  private final MateriaJoomlaService materiaJoomlaService;
  private final ConfiguracoesProperties properties;
  private final CargaMassaRepository repository;
  private final ObjectMapper objectMapper;

  public List<MapaPerguntaDTO> processar(final RequisicaoCaragMassaDTO request) {

    log.info("Inicio processamento ".concat(LocalDateTime.now().toString()));
    final String uuid = UUID.randomUUID().toString();
    final List<MapaPerguntaDTO> itens = gerarMapaPerguntasService.gerarMapa(request.getIdeias(), uuid);

    // new Thread(new ProcessoLote(properties, materiaJoomlaService,
    // gerarMateriaService, uuid, request, itens)).start();

    try {
      repository.save(new CargaMassaEntity(null, uuid, StatusCargaEnum.PROCESSAR,
          SugerirMateriaUtils.getLocalDateTime(request.getDataInicioPublicacao(),
              request.getHoarios().get(0).getHoraio()),
          SugerirMateriaUtils.getLocalDateTime(request.getDataFimPublicacao(), request.getHoarios().get(0).getHoraio()),
          null, null, null, objectMapper.writeValueAsString(request)));
    } catch (final JsonProcessingException e) {
      log.info("Falha ao realizar o salvamento do processo em lote ".concat(uuid));
    }

    log.info("Concluído processamento dos mapas e vai iniciar os da materias em lote"
        .concat(LocalDateTime.now().toString()));
    return itens;
  }

}