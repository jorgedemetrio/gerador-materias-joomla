/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.cargaemmassa.service;

import static java.util.Objects.nonNull;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import org.springframework.stereotype.Component;

import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormaterias.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormaterias.materia.exception.BusinessException;
import com.br.sobieskiproducoes.geradormaterias.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormaterias.materia.service.CategoriaService;
import com.br.sobieskiproducoes.geradormaterias.materia.service.MateriaJoomlaService;
import com.br.sobieskiproducoes.geradormaterias.materia.service.TagService;
import com.br.sobieskiproducoes.geradormaterias.utils.MateriaUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 1 de mar. de 2024 20:40:39
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Component
public class ProcessamentoPublicarMateriasService {
  private final ConfiguracoesProperties properties;
  private final MateriaJoomlaService materiaJoomlaService;
  private final MateriaRepository materiaRepository;
  private final CategoriaService categoriaService;
  private final TagService tagService;

  @Transactional
  public void processar() throws BusinessException {
    log.info("Atualizando banco com informações do Joomla");
    final List<MateriaEntity> materias = materiaRepository.buscarMateriasPublicar();
    if (nonNull(materias) && !materias.isEmpty()) {
      categoriaService.atualizarBancoCategoria();
      tagService.atualizarBancoTag();
    }
    for (final MateriaEntity materiaEntity : materias) {
      processarMateriaNoJoomla(materiaEntity);
    }

  }

  /**
   * @param data
   * @param propostaMateriaDTO
   * @param materia
   * @throws BusinessException
   */
  private void processarMateriaNoJoomla(final MateriaEntity materiaEntity)
      throws BusinessException {
    if (StatusProcessamentoEnum.PROCESSADO.equals(materiaEntity.getStatus())) {
      return;
    }
    try {
      new File(properties.getCargaDadosImagens().getImagens().getPastaImagemMaterias()
          .concat(MateriaUtils.pathCategoria(materiaEntity.getCategoria()))).mkdirs();
    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Erro ao criar a pasta da materia : ".concat(materiaEntity.getId().toString()), ex);
    }
    if (nonNull(materiaJoomlaService.publicarMateriaJoomla(materiaEntity))) {
      log.info("Publicado a materia :" + materiaEntity.getIdJoomla());
//      erroInterno = true;
//    } else {
//      final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//      def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
//      transactionManager.commit(transactionManager.getTransaction(def));
    }

  }

}
