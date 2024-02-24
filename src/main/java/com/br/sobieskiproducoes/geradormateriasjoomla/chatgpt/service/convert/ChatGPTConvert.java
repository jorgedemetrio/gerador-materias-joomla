/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service.convert;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.ChoicesDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.model.LogDialogoChatGPTEntity;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 17:50:59
 * @version 1.0.0
 */
@Mapper(imports = { Objects.class, Collectors.class, Arrays.class })
public interface ChatGPTConvert {


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

}
