/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.ArtigoJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.TagJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosTagJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception.BusinessException;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception.ObjectoJaExiteNoBancoBusinessException;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.TagRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.MateriaConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.utils.SugerirMateriaUtils;

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
  private final TagRepository tagRepository;

  private final MateriaConvert convert;

  private final TagJoomlaClient clientTag;
  private final ArtigoJoomlaClient clientArtigo;

  public GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>> publicarMateriaJoomla(
      final Long id, final LocalDateTime publicar) throws BusinessException {
    log.info("Inicio de publicação de materia no Joomla ID".concat(id.toString()));

    final MateriaEntity entity = materiaRepository.findById(id).get();

    if (nonNull(entity.getIdJoomla())) {
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

    entity.getTags().forEach(n -> {
      if (isNull(n.getIdJoomla())) {
        final AtributosTagJoomlaDTO tag = new AtributosTagJoomlaDTO();
        tag.setAlias(nonNull(n.getApelido()) && !n.getApelido().isBlank() ? n.getApelido()
            : SugerirMateriaUtils.normalizeText(n.getTitulo()));
        tag.setTitle(n.getTitulo());
        tag.setPublished(1L);
        tag.setAccess(1L);
        tag.setDescription("");
        tag.setPath(tag.getAlias());
        tag.setAccessTitle("Public");
        tag.setParentId(1L);
        tag.setLanguage("*");
        final GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> retornoTag = clientTag
            .gravarTag(tag);
        n.setIdJoomla(retornoTag.getData().getAttributes().getId());
        n.setApelido(tag.getAlias());
        tagRepository.save(n);
      }
    });


    entity.getTags().forEach(n -> item.getTags().put(n.getId().toString(), n.getTitulo()));
    if (isNull(entity.getTituloSelecionado()) || entity.getTituloSelecionado() <= 1
        || entity.getTituloSelecionado() > 3) {
      entity.setApelido(SugerirMateriaUtils.normalizeText(entity.getTitulo1()));
      entity.setTituloSelecionado(1);
    } else {
      switch (entity.getTituloSelecionado()) {
      case 2:
        entity.setApelido(SugerirMateriaUtils.normalizeText(entity.getTitulo1()));
        break;
      case 3:
        entity.setApelido(SugerirMateriaUtils.normalizeText(entity.getTitulo1()));
        break;
      }
    }
    final GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>> artigo = clientArtigo.gravar(item);
    entity.setIdJoomla(artigo.getData().getAttributes().getId());
    materiaRepository.save(entity);
    return artigo;
  }

}
