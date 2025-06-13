/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.service.convert;

import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.br.sobieskiproducoes.geradormaterias.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosCategoriaJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.controller.dto.CategoriaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.model.CategoriaEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:50:59
 * @version 1.0.0
 */
@Mapper(imports = { Objects.class })
public interface CategoriaConvert {

  @Mapping(target = "pai", ignore = true)
  CategoriaDTO convert(CategoriaEntity categoria);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "pai.id", ignore = true)
  @Mapping(target = "pai.titulo", ignore = true)
  @Mapping(target = "pai.apelido", ignore = true)
  @Mapping(target = "idJoomla", source = "id")
  @Mapping(target = "titulo", source = "attributes.title")
  @Mapping(target = "apelido", source = "attributes.alias")
  @Mapping(target = "pai.idJoomla", source = "attributes.parentId")
  CategoriaEntity convert(GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO> categoria);

  @Mapping(target = "pai.pai", ignore = true)
  CategoriaDTO convertCategoriaDTO(CategoriaEntity categoria);

  GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO> convertCategoriaEntityToGenericoJoomlaDataDTO(CategoriaEntity categoriaEntity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "apelido", source = "apelido", conditionExpression = "java(Objects.nonNull(categoria.getApelido()) && !categoria.getApelido().isBlank())")
  @Mapping(target = "titulo", source = "titulo", conditionExpression = "java(Objects.nonNull(categoria.getTitulo()) && !categoria.getTitulo().isBlank())")
  @Mapping(target = "idJoomla", source = "idJoomla ", conditionExpression = "java(Objects.nonNull(categoria.getIdJoomla()) && categoria.getIdJoomla() > 0L )")
  void merge(@NotNull @Valid CategoriaDTO categoria, @MappingTarget CategoriaEntity categoriaEntity);

  @Mapping(target = "id", ignore = true)
  CategoriaEntity toEntityNova(CategoriaDTO categoria);
}
