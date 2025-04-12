/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.ChoicesDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.model.LogDialogoChatGPTEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.joomla.AtributosArtigoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.joomla.AtributosArtigoJoomlaSalvarDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.FaqDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.MateriaDTO;
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

  /**
   * @param materia
   * @return
   */
  @Mapping(target = "tags", ignore = true)
  MateriaEntity convert(AtributosArtigoJoomlaDTO materia);

  @Mapping(target = "materia", ignore = true)
  FAQEntity convert(FaqDTO materia);

  @Mapping(target = "tags", expression = "java( Objects.nonNull(materia.getTags()) ? materia.getTags().stream().map(c->c.getTitulo()).toList(): null)")
  @Mapping(target = "titulos", expression = "java( Arrays.asList( materia.getTitulo1(), materia.getTitulo2(), materia.getTitulo3() ) )")
  PropostaMateriaDTO convert(MateriaEntity materia);


  MateriaDTO toDTO(MateriaEntity materia);

  @Mapping(target = "titulo1", expression = "java( (Objects.nonNull(materia.getTitulos()) && !materia.getTitulos().isEmpty()) ? materia.getTitulos().get(0) : null )")
  @Mapping(target = "titulo2", expression = "java( (Objects.nonNull(materia.getTitulos()) && !materia.getTitulos().isEmpty()) ? materia.getTitulos().get(1) : null )")
  @Mapping(target = "titulo3", expression = "java( (Objects.nonNull(materia.getTitulos()) && !materia.getTitulos().isEmpty()) ? materia.getTitulos().get(2) : null )")
  MateriaEntity convert(PropostaMateriaDTO materia);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "idChatGpt", source = "dto.id")
  @Mapping(target = "object", source = "dto.object")
  @Mapping(target = "created", source = "dto.created")
  @Mapping(target = "model", source = "dto.model")
  @Mapping(target = "index", source = "choice.index")
  @Mapping(target = "role", source = "choice.message.role")
  @Mapping(target = "logprobs", source = "choice.logprobs")
  @Mapping(target = "finishReason", source = "choice.finishReason")
  @Mapping(target = "promptTokens", source = "dto.usage.promptTokens")
  @Mapping(target = "completionTokens", source = "dto.usage.completionTokens")
  @Mapping(target = "totalTokens", source = "dto.usage.totalTokens")
  @Mapping(target = "systemFingerprint", source = "dto.systemFingerprint")
  @Mapping(target = "data", expression = "java(LocalDateTime.now())")
  @Mapping(target = "inicio", source = "inicio")
  @Mapping(target = "pergunta", source = "pergunta")
  @Mapping(target = "content", source = "choice.message.content")
  @Mapping(target = "uuid", source = "uuid")
  LogDialogoChatGPTEntity convert(final RepostaResponseDTO dto, ChoicesDTO choice, LocalDateTime inicio,
      String pergunta, String uuid);

  @Mapping(target = "titulo", expression = "java(tag)")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "idJoomla", ignore = true)
  @Mapping(target = "apelido", ignore = true)
  @Mapping(target = "materias", ignore = true)
  TagEntity convert(String tag);

  @Mapping(target = "tags", ignore = true)
  MateriaEntity convertJoomla(AtributosArtigoJoomlaDTO materia);

  @Mapping(target = "tags", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "images", ignore = true)
  @Mapping(target = "metadesc", source = "metaDescricao")
  @Mapping(target = "metakey", source = "keywords")
  @Mapping(target = "title", expression = "java((Objects.isNull(materia.getTituloSelecionado()) || materia.getTituloSelecionado() == 1? "
      + "  materia.getTitulo1() : materia.getTituloSelecionado() == 2 ? materia.getTitulo2() : materia.getTitulo3()) )")

  @Mapping(target = "publishUp", source = "publicar", dateFormat = "yyyy-MM-dd HH:mm:ss")
  @Mapping(target = "featured", expression = "java(1)")
  @Mapping(target = "state", expression = "java(1)")
  @Mapping(target = "access", expression = "java(1)")
  @Mapping(target = "fulltext", source = "materia")
  @Mapping(target = "typeAlias", expression = "java(\"com_content.article\")")
  @Mapping(target = "alias", source = "apelido")
  @Mapping(target = "version", expression = "java(9)")
  @Mapping(target = "relationships", ignore = true)
  @Mapping(target = "catid", source = "categoria.idJoomla")
  @Mapping(target = "introtext", source = "primeiroParagrafo")

  @Mapping(target = "created", expression = "java( Objects.nonNull(materia.getCriadoJoomla()) ? materia.getCriadoJoomla() : materia.getPublicar() )", dateFormat = "yyyy-MM-dd HH:mm:ss")

  AtributosArtigoJoomlaSalvarDTO convertJoomla(MateriaEntity materia);

  @Mapping(target = "materia", source = "content")
  @Mapping(target = "uuid", source = "uuid")
  PropostaMateriaDTO copy(PropostaMateriaDTO item, String uuid, String content);

  /**
   * @param attributes
   * @param materiaEntity
   */
  @Mapping(target = "tags", ignore = true)
  void merge(AtributosArtigoJoomlaDTO attributes, @MappingTarget MateriaEntity materiaEntity);

}
