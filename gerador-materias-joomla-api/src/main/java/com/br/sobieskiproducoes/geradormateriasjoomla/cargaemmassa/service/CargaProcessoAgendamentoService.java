/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.controller.dto.RequisicaoCaragMassaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.model.CargaMassaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.repository.CargaMassaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.dto.StatusProcessamentoEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 26 de fev. de 2024 20:16:51
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Component
public class CargaProcessoAgendamentoService {

  private static boolean RODANDO = Boolean.FALSE;
  private final CargaMassaRepository repository;
  private final ObjectMapper objectMapper;

  private final CargaProcessoMateriaService processo;

  // Roda sozinha a cada minuto 30 minutos depois da ultima vez que rodou
  @Scheduled(fixedDelay = 30000)
  public void processar() {
    if (!RODANDO) {
      try {
        RODANDO = Boolean.TRUE;
        final List<CargaMassaEntity> itens = repository.pegarCarga();
        RequisicaoCaragMassaDTO item = null;
        for (final CargaMassaEntity cargaMassaEntity : itens) {
          try {
            cargaMassaEntity.setExecutadoInicio(LocalDateTime.now());
            item = objectMapper.readValue(cargaMassaEntity.getRequisicao(), RequisicaoCaragMassaDTO.class);
            if (processo.iniciarProcesso(item, cargaMassaEntity.getUuid())) {
              cargaMassaEntity.setStatus(StatusProcessamentoEnum.PROCESSADO);
            } else {
              cargaMassaEntity.setStatus(StatusProcessamentoEnum.ERRO);
              cargaMassaEntity.setNota("Errop em um processamento interno. ");
            }
          } catch (final Exception e) {
            cargaMassaEntity.setStatus(StatusProcessamentoEnum.ERRO);
            cargaMassaEntity.setNota(e.getLocalizedMessage());
            log.log(Level.SEVERE, e.getMessage(), e);
          }
          cargaMassaEntity.setExecutadoFim(LocalDateTime.now());
          repository.save(cargaMassaEntity);
        }
      } catch (final Throwable e) {// Casso ocorra algum continua o processo só gere log

        log.log(Level.SEVERE, e.getMessage(), e);
      }
    }
    RODANDO = Boolean.FALSE;
  }

}
