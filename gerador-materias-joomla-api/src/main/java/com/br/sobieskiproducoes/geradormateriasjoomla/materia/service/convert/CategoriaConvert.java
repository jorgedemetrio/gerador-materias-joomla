/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosCategoriaJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:50:59
 * @version 1.0.0
 */
@Mapper
public interface CategoriaConvert {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "pai.id", ignore = true)
  @Mapping(target = "pai.titulo", ignore = true)
  @Mapping(target = "pai.apelido", ignore = true)
  @Mapping(target = "idJoomla", source = "id")
  @Mapping(target = "titulo", source = "attributes.title")
  @Mapping(target = "apelido", source = "attributes.alias")
  @Mapping(target = "pai.idJoomla", source = "attributes.parentId")
  CategoriaEntity convert(Object categoriaEntity);
  GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO> convertCategoriaEntityToGenericoJoomlaDataDTO(CategoriaEntity categoriaEntity);


  @Mapping(target = "pai.pai", ignore = true)
  CategoriaEntity convertCategoriaDTO(CategoriaEntity categoria);
  void merge(@NotNull @Valid CategoriaEntity categoria, CategoriaEntity categoriaEntity);
}
