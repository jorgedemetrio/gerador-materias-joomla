/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.MateriaConstants;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.TagJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosTagJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.TagDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.TagEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.TagRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.TagConvert;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 22:03:54
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class TagService {

  private final TagRepository repository;
  private final TagConvert convert;
  private final TagJoomlaClient client;
  private final ConfiguracoesProperties properties;

  public Boolean apagar(@NotNull final Long id) {
    final Optional<TagEntity> tagEntityOpt = repository.findById(id);
    if (!tagEntityOpt.isPresent()) {
      return Boolean.FALSE;
    }
    repository.delete(tagEntityOpt.get());
    log.info("Apagado a Tag ".concat(id.toString()));
    return Boolean.TRUE;
  }

  /**
   *
   * @return O univero de registros atualizados.
   */
  @Transactional
  public Map<String, Integer> atualizarBancoTag() {
    final List<TagEntity> itens = new ArrayList<>();
    GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> consulta = null;
    int offset = 0;
    final Map<String, Integer> retorno = new HashMap<>();
    TagEntity tagEntity = null;
    Optional<TagEntity> tagEntityOpt = null;
    // Busca as Categorias
    do {
      if (isNull(consulta)) {
        // Primeira pagina
        consulta = client.getTags();
      } else {
        // Faz a proxima busca, pegando o next page e removendo a URL
        consulta = client.getTags(String.valueOf(offset * 20), "20");

      }
      offset++;
      if (nonNull(consulta) && nonNull(consulta.getData()) && !consulta.getData().isEmpty()) {

        for (final GenericoJoomlaDataDTO<AtributosTagJoomlaDTO> tag : consulta.getData()) {
          // Só salva Tag no banco que pertence ao idioma padrão do site.
          if (isNull(tag.getAttributes())
              || tag.getAttributes().getLanguage().equals(properties.getJoomla().getIdioma())
              || MateriaConstants.SEM_IDIOMA.equals(tag.getAttributes().getLanguage())) {
            tagEntityOpt = repository.buscarPorApelidoTitulo(tag.getAttributes().getAlias(),
                tag.getAttributes().getTitle());
            if (tagEntityOpt.isPresent()) {
              tagEntity = tagEntityOpt.get();
              convert.merge(tag.getAttributes(), tagEntity);
            } else {
              tagEntity = convert.convertJoomla(tag.getAttributes());
            }

            try {
              repository.save(tagEntity);
            } catch (final Exception ex) {
              log.log(Level.SEVERE, ex.getLocalizedMessage(), ex.getCause());
            }
            itens.add(tagEntity);
          }
        }

      }

    } while (nonNull(consulta) && nonNull(consulta.getLinks()) && nonNull(consulta.getLinks().getNext())
        && nonNull(consulta.getData()) && !consulta.getLinks().getNext().isBlank());

    retorno.put("total", itens.size());

    log.info("Gravando lista de %d de Tags.".formatted(itens.size()));
    retorno.put("processados", itens.size());
    return retorno;
  }

  public TagDTO buscarPorId(final Long id) {
    final Optional<TagEntity> tagEntityOpt = repository.findById(id);
    if (!tagEntityOpt.isPresent()) {
      return null;
    }
    return convert.convert(tagEntityOpt.get());
  }

  public List<TagDTO> buscarPorTitulo(final String titulo, final Integer pagina) {
    final PageRequest page = PageRequest.of(pagina, MateriaConstants.MAX_INTENS_PER_PAGE,
        Sort.by("titulo").ascending());
    // If ternario
    return (isNull(titulo) || titulo.isBlank() ? repository.findAll(page)
        : repository.findByTituloContainingIgnoreCase(titulo, page)).stream().map(convert::convert)
            .collect(Collectors.toList());

  }

  public TagDTO gravar(@NotNull @Valid final TagDTO tag) {
    TagEntity tagEntity = null;

    // Atualiza caso já tenha um ID no banco
    if (nonNull(tag.getId()) && tag.getId() > 0) {
      final Optional<TagEntity> tagEntityOpt = repository.findById(tag.getId());
      if (tagEntityOpt.isPresent()) {
        tagEntity = tagEntityOpt.get();
        log.info("Fez merge do Tag id: ".concat(tag.getId().toString()));
        convert.merge(tag, tagEntity);
      }
    }

    // Caso não tenha ID entende-se que é um Novo Registro
    if (isNull(tagEntity)) {
      log.info("Novo registro");
      tagEntity = convert.convert(tag);
    }

    return convert.convert(repository.save(tagEntity));
  }

}
