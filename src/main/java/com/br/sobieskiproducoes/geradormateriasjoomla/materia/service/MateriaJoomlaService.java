/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.ArtigoJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception.BusinessException;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception.ObjectoJaExiteNoBancoBusinessException;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.MateriaConvert;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:50:06
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class MateriaJoomlaService {

  private final MateriaRepository materiaRepository;

  private final MateriaConvert convert;

  private final ArtigoJoomlaClient clientArtigo;

  public String publicarMateriaJoomla(final Long id, final LocalDateTime publicar) throws BusinessException {
    log.info("Inicio de publicação de materia no Joomla ID".concat(id.toString()));



    final MateriaEntity entity = materiaRepository.findById(id).get();

    if(nonNull(entity.getIdJoomla())) {
      throw BusinessException.build().classe(ObjectoJaExiteNoBancoBusinessException.class)
          .mensagem("Erro ao tentar acessar ").builder();
    }
    if (nonNull(publicar)) {
      entity.setPublicar(publicar);
      materiaRepository.save(entity);
    }



    final AtributosArtigoJoomlaDTO item = convert.convertJoomla(entity);
    item.setTags(new HashMap<>());

    // Mapeando Tags
    entity.getTags().forEach(n -> item.getTags().put(n.getId().toString(), n.getTitulo()));

    return clientArtigo.gravar(item);
  }


}
