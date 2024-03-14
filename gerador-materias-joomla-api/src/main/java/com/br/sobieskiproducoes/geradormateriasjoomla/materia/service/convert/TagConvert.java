/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert;

import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosTagJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.TagDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.TagEntity;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 22:18:19
 * @version 1.0.0
 */
@Mapper(imports = { Objects.class })
public interface TagConvert {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "idJoomla", source = "idJoomla")
  @Mapping(target = "titulo", source = "titulo")
  @Mapping(target = "apelido", source = "apelido")
  TagDTO convert(TagEntity tag);

  @Mapping(target = "idJoomla", source = "id")
  @Mapping(target = "titulo", source = "title")
  @Mapping(target = "apelido", source = "alias")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "materias", ignore = true)
  TagEntity convertJoomla(AtributosTagJoomlaDTO tag);

  @Mapping(target = "id", source = "idJoomla")
  @Mapping(target = "title", source = "titulo")
  @Mapping(target = "alias", source = "apelido")
  AtributosTagJoomlaDTO convertJoomla(TagEntity tag);

  @Mapping(target = "materias", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "apelido", source = "alias", conditionExpression = "java(Objects.nonNull(tag.getApelido()) && !tag.getApelido().isBlank())")
  @Mapping(target = "titulo", source = "title", conditionExpression = "java(Objects.nonNull(tag.getTitulo()) && !tag.getTitulo().isBlank())")
  @Mapping(target = "idJoomla", source = "id ", conditionExpression = "java(Objects.nonNull(tag.getId()) && tag.getId() > 0L )")
  void merge(AtributosTagJoomlaDTO atributosArtigoJoomlaDTO, @MappingTarget TagEntity tag);


  @Mapping(target = "materias", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "apelido", source = "apelido", conditionExpression = "java(Objects.nonNull(tag.getApelido()) && !tag.getApelido().isBlank())")
  @Mapping(target = "titulo", source = "titulo", conditionExpression = "java(Objects.nonNull(tag.getTitulo()) && !tag.getTitulo().isBlank())")
  @Mapping(target = "idJoomla", source = "idJoomla ", conditionExpression = "java(Objects.nonNull(tag.getIdJoomla()) && tag.getIdJoomla() > 0L )")
  void merge(TagDTO tag, @MappingTarget TagEntity src);

  @Mapping(target = "materias", ignore = true)
  @Mapping(target = "id", ignore = true)
  TagEntity toEntityNova(TagDTO tag);
}
