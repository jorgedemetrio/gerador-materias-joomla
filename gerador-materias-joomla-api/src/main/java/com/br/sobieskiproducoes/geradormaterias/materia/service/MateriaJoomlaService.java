/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormaterias.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormaterias.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormaterias.dto.StatusProcessamentoEnum;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.JoomlaConfigurationEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosArtigoJoomlaSalvarDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosArtigoSalvoJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosTagJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.ImagesMateriaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.joomla.GravarEntityesConsumerService;
import com.br.sobieskiproducoes.geradormaterias.materia.exception.BusinessException;
import com.br.sobieskiproducoes.geradormaterias.materia.exception.ObjectoJaExiteNoBancoBusinessException;
import com.br.sobieskiproducoes.geradormaterias.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormaterias.materia.repository.TagRepository;
import com.br.sobieskiproducoes.geradormaterias.materia.service.convert.MateriaConvert;
import com.br.sobieskiproducoes.geradormaterias.utils.MateriaUtils;

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

  private static final DateTimeFormatter DATTE_TIME_FORMATTER_ALIAS = DateTimeFormatter.ofPattern("yyyy-MM-dd-");
  private final MateriaRepository materiaRepository;

  private final TagRepository tagRepository;

  private final MateriaConvert convert;

  private final GravarEntityesConsumerService consumerService;

  private final ConfiguracoesProperties properties;

  public GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> publicarMateriaJoomla(final Long id,
      final JoomlaConfigurationEntity config) throws BusinessException {

    return publicarMateriaJoomla(materiaRepository.findById(id).get(), config);
  }

  public GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> publicarMateriaJoomla(final MateriaEntity entity,
      final JoomlaConfigurationEntity config) throws BusinessException {
    log.info("Inicio de publicação de materia no Joomla ID".concat(entity.getId().toString()));

    GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> artigo = null;
    try {
      if (isNull(entity.getMateria()) || entity.getMateria().isBlank() || entity.getMateria().trim().length() < 10) {
        log.info("Materia não pode ser rgavadar poser vazia" + entity.toString());
        return null;
      }
      if (nonNull(entity.getIdJoomla())) {
        throw BusinessException.build().classe(ObjectoJaExiteNoBancoBusinessException.class).mensagem("Erro ao tentar acessar ").builder();
      }
      if (nonNull(entity.getPublicar())) {
        entity.setApelido(entity.getPublicar().format(DATTE_TIME_FORMATTER_ALIAS) + entity.getApelido());
        materiaRepository.save(entity);
      }

      final AtributosArtigoJoomlaSalvarDTO item = convert.convertJoomla(entity);
      item.setTags(new ArrayList<>());
      item.setLanguage(config.getIdioma());

      // Mapeando Tags

      entity.getTags().forEach(n -> {
        if (isNull(n.getIdJoomla())) {
          final AtributosTagJoomlaDTO tag = new AtributosTagJoomlaDTO();
          final String alias = (nonNull(entity.getPublicar()) ? entity.getPublicar().format(DATTE_TIME_FORMATTER_ALIAS) : "")
              + (nonNull(n.getApelido()) && !n.getApelido().isBlank() ? n.getApelido() : MateriaUtils.normalizeText(n.getTitulo()));

          tag.setAlias(alias);
          tag.setTitle(n.getTitulo());
          tag.setPublished(1L);
          tag.setAccess(1L);
          tag.setDescription("");
          tag.setPath(tag.getAlias());
          tag.setAccessTitle("Public");
          tag.setParentId(1L);
          tag.setLanguage(config.getIdioma());
          try {
            final GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> retornoTag = consumerService.gravarTag(tag, config);
            n.setIdJoomla(retornoTag.getData().getAttributes().getId());
            n.setApelido(tag.getAlias());
            tagRepository.save(n);
          } catch (final HttpClientErrorException ex) {
            log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
//            if (!ex.getLocalizedMessage().contains("same alias")) {
//
//            }
          } catch (final RestClientException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
          }
        }
        if (nonNull(n.getIdJoomla()) && n.getIdJoomla() > 0) {
          item.getTags().add(n.getIdJoomla().toString());
        }
      });

      if (isNull(entity.getTituloSelecionado()) || entity.getTituloSelecionado() <= 1 || entity.getTituloSelecionado() > 3) {
        entity.setApelido(MateriaUtils.normalizeText(entity.getTitulo1()));
        entity.setTituloSelecionado(1);
      } else {
        switch (entity.getTituloSelecionado()) {
        case 2:
          entity.setApelido(MateriaUtils.normalizeText(entity.getTitulo1()));
          break;
        case 3:
          entity.setApelido(MateriaUtils.normalizeText(entity.getTitulo1()));
          break;
        }
      }

      // Imagens
      final String diretorioImagens = MateriaUtils.pathCategoria(entity.getCategoria());
      final String nomeArquivo = diretorioImagens.concat("/").concat(item.getAlias()).concat(".jpg");
      final String nomeArquivoCompleto = properties.getCargaDadosImagens().getImagens().getUrl() + nomeArquivo
          + properties.getCargaDadosImagens().getImagens().getConstante() + nomeArquivo + "?width=986&height=515";

      item.setImages(new ImagesMateriaDTO());
      item.getImages().setImageIntroAlt(entity.getTitulo2());
      item.getImages().setImageFulltextAlt(entity.getTitulo3());
      item.getImages().setImageFulltext(nomeArquivoCompleto);
      item.getImages().setImageIntro(nomeArquivoCompleto);
      try {
        artigo = consumerService.gravarArtigo(item, config);
        entity.setExportado(LocalDateTime.now());
        entity.setIdJoomla(nonNull(artigo.getData().getId()) ? Long.valueOf(artigo.getData().getId()) : artigo.getData().getAttributes().getId());
        entity.setStatus(StatusProcessamentoEnum.PROCESSADO);
      } catch (final HttpClientErrorException ex) {
        log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        if (!ex.getLocalizedMessage().contains("same alias")) {
          entity.setStatus(StatusProcessamentoEnum.ERRO);
        }
      }

    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Erro ao publicar a materia : " + entity.toString() + "\n\n" + ex.getLocalizedMessage(), ex.getCause());
      entity.setStatus(StatusProcessamentoEnum.ERRO);
    }
    materiaRepository.save(entity);
    return artigo;
  }

}
