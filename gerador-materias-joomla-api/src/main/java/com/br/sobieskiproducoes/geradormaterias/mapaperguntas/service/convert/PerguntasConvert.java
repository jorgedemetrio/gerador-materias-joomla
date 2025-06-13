/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.mapaperguntas.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.controller.dto.MapaPerguntaDTO;
import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.model.MapaPerguntaEntity;
import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.model.SubMapaPerguntasEntity;
import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.model.TermosMapaPerguntaEntity;
import com.br.sobieskiproducoes.geradormaterias.mapaperguntas.service.dto.MapaPerguntaRetornoChatGPTDTO;

/**
 * @author Jorge Demetrio
 * @since 24 de fev. de 2024 13:39:34
 * @version 1.0.0
 */
@Mapper
public interface PerguntasConvert {

  @Mapping(target = "termos", ignore = true)
  @Mapping(target = "perguntasAlternativas", ignore = true)
  @Mapping(target = "categoria.pai", ignore = true)
  MapaPerguntaDTO convert(MapaPerguntaEntity perunta);

  @Mapping(target = "termos", ignore = true)
  @Mapping(target = "perguntasAlternativas", ignore = true)
  MapaPerguntaEntity convert(MapaPerguntaRetornoChatGPTDTO perunta);

  @Mapping(target = "termos", ignore = true)
  @Mapping(target = "perguntasAlternativas", ignore = true)
  MapaPerguntaDTO toMapaPerguntaDTO(MapaPerguntaEntity perunta);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "pergunta", source = "pergunta")
  @Mapping(target = "peguntaPrincipal", ignore = true)
  SubMapaPerguntasEntity toSubMapaPerguntasEntity(String pergunta);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "termo", source = "termo")
  @Mapping(target = "peguntaPrincipal", ignore = true)
  TermosMapaPerguntaEntity toTermosMapaPerguntaEntity(String termo);

}
