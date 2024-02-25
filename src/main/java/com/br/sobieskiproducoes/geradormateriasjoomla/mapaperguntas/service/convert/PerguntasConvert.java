/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.MapaPerguntaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.model.SubMapaPerguntasEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.mapaperguntas.service.dto.MapaPerguntaRetornoChatGPTDTO;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 13:39:34
 * @version 1.0.0
 */
@Mapper
public interface PerguntasConvert {

  @Mapping(target = "perguntasAlternativas", ignore = true)
  @Mapping(target = "categoria.pai", ignore = true)
  MapaPerguntaDTO convert(MapaPerguntaEntity perunta);

  @Mapping(target = "perguntasAlternativas", ignore = true)
  MapaPerguntaEntity convert(MapaPerguntaRetornoChatGPTDTO perunta);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "pergunta", source = "perunta")
  @Mapping(target = "peguntaPrincipal", ignore = true)
  SubMapaPerguntasEntity convert(String perunta);


}
