/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.FaqDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.FAQEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.TagEntity;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:50:59
 * @version 1.0.0
 */
@Mapper(imports = { Objects.class, Collectors.class, Arrays.class })
public interface MateriaConvert {

  @Mapping(target = "materia", ignore = true)
  FAQEntity convert(FaqDTO materia);

  @Mapping(target = "tags", expression = "java( Objects.nonNull(materia.getTags()) ? materia.getTags().stream().map(c->c.getTitulo()).collect(Collectors.toList()): null)")
  @Mapping(target = "titulos", expression = "java( Arrays.asList( materia.getTitulo1(), materia.getTitulo2(), materia.getTitulo3() ) )")
  PropostaMateriaDTO convert(MateriaEntity materia);

  @Mapping(target = "titulo1", expression = "java( (Objects.nonNull(materia.getTitulos()) && !materia.getTitulos().isEmpty()) ? materia.getTitulos().get(0) : null )")
  @Mapping(target = "titulo2", expression = "java( (Objects.nonNull(materia.getTitulos()) && !materia.getTitulos().isEmpty()) ? materia.getTitulos().get(1) : null )")
  @Mapping(target = "titulo3", expression = "java( (Objects.nonNull(materia.getTitulos()) && !materia.getTitulos().isEmpty()) ? materia.getTitulos().get(2) : null )")
  MateriaEntity convert(PropostaMateriaDTO materia);

  @Mapping(target = "titulo", expression = "java(tag)")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "idJoomla", ignore = true)
  @Mapping(target = "apelido", ignore = true)
  @Mapping(target = "materias", ignore = true)
  TagEntity convert(String tag);

  @Mapping(target = "tags", ignore = true)
  AtributosArtigoJoomlaDTO convertJoomla(MateriaEntity materia);

}
