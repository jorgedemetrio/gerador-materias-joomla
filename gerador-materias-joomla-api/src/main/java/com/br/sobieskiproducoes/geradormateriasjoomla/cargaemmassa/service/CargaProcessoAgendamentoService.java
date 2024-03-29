/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.cargaemmassa.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;

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

  private final CargaMassaRepository repository;
  private final ObjectMapper objectMapper;

  private final ProcessamentoCriarMateriasService processoCriacaoMaterias;
  private final ProcessamentoPublicarMateriasService processamentoPublicarMateriasService;



  // Roda sozinha a cada minuto 30 minutos depois da ultima vez que rodou
  // @Scheduled(fixedDelay = 30000) // ${configuracao.batch.gerar-materia.delay})
  public void processarCriacaoMaterias() {
    try {
      final List<CargaMassaEntity> itens = repository.pegarCarga();
      RequisicaoCaragMassaDTO item = null;
      for (final CargaMassaEntity cargaMassaEntity : itens) {
        try {
          cargaMassaEntity.setExecutadoInicio(LocalDateTime.now());
          item = objectMapper.readValue(cargaMassaEntity.getRequisicao(), RequisicaoCaragMassaDTO.class);
          if (processoCriacaoMaterias.iniciarProcesso(item, cargaMassaEntity.getUuid())) {
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

  // @Scheduled(fixedDelay = 3600000) // Uma vez por hora
  // @Scheduled(fixedDelay = 30000) // Uma vez por hora
  public void processarPùblicacaoMateria() {
    try {

      processamentoPublicarMateriasService.processar();
    } catch (final Throwable e) {
      log.log(Level.SEVERE, e.getMessage(), e);
    }
  }

}
