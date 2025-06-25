/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.cargaemmassa.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormaterias.cargaemmassa.model.CargaMassaEntity;
import com.br.sobieskiproducoes.geradormaterias.cargaemmassa.repository.CargaMassaRepository;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.dto.SessaoChatGPTDTO;
import com.br.sobieskiproducoes.geradormaterias.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.service.GerarMapaPerguntasService;
import com.br.sobieskiproducoes.geradormaterias.utils.MateriaUtils;
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
  private final CargaMassaRepository repository;
  private final ObjectMapper objectMapper;

  public List<MapaPerguntaDTO> processar(final RequisicaoCaragMassaDTO request, final SessaoChatGPTDTO sessao) throws Exception {

    log.info("Inicio processamento ".concat(LocalDateTime.now().toString()));
    final String uuid = UUID.randomUUID().toString();
    final List<MapaPerguntaDTO> itens = gerarMapaPerguntasService.gerarMapa(request.getIdeias(), uuid, sessao);

    try {
      repository.save(new CargaMassaEntity(null, uuid, StatusProcessamentoEnum.PROCESSAR,
          MateriaUtils.getLocalDateTime(request.getDataInicioPublicacao(), request.getHorario()),
          MateriaUtils.getLocalDateTime(request.getDataFimPublicacao(), request.getHorario()), null, null, null, objectMapper.writeValueAsString(request)));
    } catch (final JsonProcessingException e) {
      log.info("Falha ao realizar o salvamento do processo em lote ".concat(uuid));
    }

    log.info("Concluído processamento dos mapas e vai iniciar os da materias em lote".concat(LocalDateTime.now().toString()));
    return itens;
  }

}