/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.ChatGPTConsumerClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.MessageChatGPTDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.PromptRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.model.LogDialogoChatGPTEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.repository.LogDialogoChatGPTRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service.convert.ChatGPTConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;

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
  private final ConfiguracoesProperties properties;
  private final LogDialogoChatGPTRepository logDialogoChatGPTRepository;
  private final ChatGPTConvert convert;


  private void gravarLog(final RepostaResponseDTO itensDaMateriaRetornoGPT, final String uuid,
      final LocalDateTime inicio, final String pergunta) {
    try { // Grava o log da consulta.
      final List<LogDialogoChatGPTEntity> logs = itensDaMateriaRetornoGPT.getChoices().stream()
          .map(choice -> convert.convert(itensDaMateriaRetornoGPT, choice, inicio, pergunta, uuid))
          .collect(Collectors.toList());
      logs.forEach(logDialogoChatGPTRepository::save);
    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Falha ao logar mensagens do ChatGPT no banco, mensagem:".concat(pergunta)
          .concat(". Erro: ").concat(ex.getMessage()), ex);
    }

  }

  public RepostaResponseDTO pergunta(final String pergunta, final String uuid, final LocalDateTime inicio) {

    final RepostaResponseDTO resposta = client
        .conversar(new PromptRequestDTO(properties.getChatgpt().getModel(),
            Arrays.asList(new MessageChatGPTDTO(properties.getChatgpt().getRoleUser(), pergunta)),
            properties.getChatgpt().getTemperature()));

    log.info("Pergunta feita ao chatGPT: ".concat(pergunta).concat(" tokens usados ")
        .concat(resposta.getUsage().getTotalTokens().toString()));

    gravarLog(resposta, uuid, inicio, pergunta);

    return resposta;

  }

  public RepostaResponseDTO perguntas(final List<String> perguntas, final String uuid, final LocalDateTime inicio) {

    final RepostaResponseDTO resposta = client
        .conversar(new PromptRequestDTO(properties.getChatgpt().getModel(),
            perguntas.stream().map(pergunta -> new MessageChatGPTDTO(properties.getChatgpt().getRoleUser(), pergunta))
                .collect(Collectors.toList()),
            properties.getChatgpt().getTemperature()));

    log.info("Pergunta feita ao chatGPT: ".concat(perguntas.toString()).concat(" tokens usados ")
        .concat(resposta.getUsage().getTotalTokens().toString()));

    perguntas.forEach(pergunta -> gravarLog(resposta, uuid, inicio, pergunta.toString()));

    return resposta;

  }

  public RepostaResponseDTO perguntasObjeto(final List<MessageChatGPTDTO> perguntas, final String uuid,
      final LocalDateTime inicio) {

    final RepostaResponseDTO resposta = client.conversar(
        new PromptRequestDTO(
        properties.getChatgpt().getModel(), perguntas, properties.getChatgpt().getTemperature()));

    log.info("Pergunta feita ao chatGPT: ".concat(perguntas.toString()).concat(" tokens usados ")
        .concat(resposta.getUsage().getTotalTokens().toString()));

    perguntas.forEach(pergunta -> gravarLog(resposta, uuid, inicio, pergunta.toString()));

    return resposta;

  }

}
