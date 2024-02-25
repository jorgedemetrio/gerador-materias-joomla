/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.MateriaConstants;
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

  public Boolean apagar(@NotNull final Long id) {
    final Optional<TagEntity> tagEntityOpt = repository.findById(id);
    if (!tagEntityOpt.isPresent()) {
      return Boolean.FALSE;
    }
    repository.delete(tagEntityOpt.get());
    log.info("Apagado a Tag ".concat(id.toString()));
    return Boolean.TRUE;
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
        convert.copy(tag, tagEntity);
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
