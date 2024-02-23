/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.ChatGPTConsumerClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.MessageChatGPTDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.PromptRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.ChoicesDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTConfigurationProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 14:07:59
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Service
public class ChatGPTService {

  private final ChatGPTConsumerClient client;
  private final ChatGPTConfigurationProperties properties;

  public List<MessageChatGPTDTO> pergunta(final String pergunta) {

    final RepostaResponseDTO resposta = client.conversar(new PromptRequestDTO(properties.getModel(),
        Arrays.asList(new MessageChatGPTDTO(properties.getRole(), pergunta)), properties.getTemperature()));

    log.info("Pergunta feita ao chatGPT: ".concat(pergunta).concat(" tokens usados ")
        .concat(resposta.getUsage().getTotalTokens().toString()));

    return resposta.getChoices().stream().map(ChoicesDTO::getMessage).collect(Collectors.toList());

  }

}
