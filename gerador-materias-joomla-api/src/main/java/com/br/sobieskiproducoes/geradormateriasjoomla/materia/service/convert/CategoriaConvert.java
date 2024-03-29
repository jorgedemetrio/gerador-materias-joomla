/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosCategoriaJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.CategoriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.CategoriaEntity;

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
  CategoriaEntity convert(GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO> categoria);

  @Mapping(target = "pai.pai", ignore = true)
  CategoriaDTO convertCategoriaDTO(CategoriaEntity categoria);

}
