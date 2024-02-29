/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.GravarEntityesConsumerService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaSalvarDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoSalvoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosTagJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.ImagesMateriaDTO;
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

  private final GravarEntityesConsumerService consumerService;

  private final ConfiguracoesProperties properties;

  public GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> publicarMateriaJoomla(
      final Long id, final LocalDateTime publicar) throws BusinessException {

    return publicarMateriaJoomla(materiaRepository.findById(id).get(), publicar);
  }

  public GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> publicarMateriaJoomla(
      final MateriaEntity entity, final LocalDateTime publicar) throws BusinessException {
    log.info("Inicio de publicação de materia no Joomla ID".concat(entity.getId().toString()));

    GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> artigo = null;
    try {

      if (nonNull(entity.getIdJoomla())) {
        throw BusinessException.build().classe(ObjectoJaExiteNoBancoBusinessException.class)
            .mensagem("Erro ao tentar acessar ").builder();
      }
      if (nonNull(publicar)) {
        entity.setPublicar(publicar);
        materiaRepository.save(entity);
      }

      final AtributosArtigoJoomlaSalvarDTO item = convert.convertJoomla(entity);
      item.setTags(new ArrayList<>());

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


          try {
            final GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> retornoTag = consumerService
                .gravarTag(tag);
            n.setIdJoomla(retornoTag.getData().getAttributes().getId());
            n.setApelido(tag.getAlias());
            tagRepository.save(n);
          }
          catch(final RestClientException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
          }
        }
        if (nonNull(n.getIdJoomla()) && n.getIdJoomla() > 0) {
          item.getTags().add(n.getIdJoomla().toString());
        }
      });

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

      // Imagens
      final String diretorioImagens = SugerirMateriaUtils.pathCategoria(entity.getCategoria());
      final String nomeArquivo = diretorioImagens.concat("/").concat(item.getAlias()).concat(".jpg");
      final String nomeArquivoCompleto = properties.getCargaDadosImagens().getImagens().getUrl() + nomeArquivo
          + properties.getCargaDadosImagens().getImagens().getConstante() + nomeArquivo + "?width=986&height=515";

      item.setImages(new ImagesMateriaDTO());
      item.getImages().setImageIntroAlt(entity.getTitulo2());
      item.getImages().setImageFulltextAlt(entity.getTitulo3());
      item.getImages().setImageFulltext(nomeArquivoCompleto);
      item.getImages().setImageIntro(nomeArquivoCompleto);

      artigo = consumerService.gravarArtigo(item);
      entity.setIdJoomla(artigo.getData().getAttributes().getId());
      entity.setStatus(StatusProcessamentoEnum.PROCESSADO);

    } catch (final Exception ex) {
      log.log(Level.SEVERE, ex.getMessage(), ex);
      entity.setStatus(StatusProcessamentoEnum.ERRO);
    }
    materiaRepository.save(entity);
    return artigo;
  }

}
