/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.chatgpt.service;

import static com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.RunnerStatuEnum.COMPLETED;
import static com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.RunnerStatuEnum.FAILED;
import static com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.RunnerStatuEnum.INCOMPLETO;
import static java.util.Objects.isNull;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.ChatGPTConsumerClient;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.request.MessageChatGPTDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.request.PromptRequestDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.DeleteThreadDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.MensagemPostedResponseDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.NextStepDataResponseDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.NextStepResponseDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.RunnerResponseDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.dto.SessaoChatGPTDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.repository.LogDialogoChatGPTRepository;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.service.convert.ChatGPTConvert;
import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.ConfiguracoesEntity;

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

  private static final int AGUARDAR = 2000;
  private final ChatGPTConsumerClient client;
  private final ConfiguracoesProperties properties;
  private final LogDialogoChatGPTRepository logDialogoChatGPTRepository;

  private final ChatGPTConvert convert;

  /**
   * @param runnerResponseDTO
   * @param chatgptRunnerId
   * @return
   * @throws InterruptedException
   */
  @SuppressWarnings("static-access")
  private NextStepResponseDTO existeResposta(RunnerResponseDTO runnerResponseDTO, final String chatgptRunnerId, final SessaoChatGPTDTO sessao)
      throws InterruptedException {
    while (!COMPLETED.equals(runnerResponseDTO.getStatus()) && !INCOMPLETO.equals(runnerResponseDTO.getStatus())) {
      Thread.currentThread().sleep(AGUARDAR);
      runnerResponseDTO = client.lerRunner(chatgptRunnerId, sessao);
      if (FAILED.equals(runnerResponseDTO.getStatus())) {
        log.log(Level.SEVERE, "ERROR [" + runnerResponseDTO.getLastError().getCode() + "]: " + runnerResponseDTO.getLastError().getMessage());
        return null;
      }
    }

    return client.proximoPasso(chatgptRunnerId, runnerResponseDTO.getStatus(), sessao);
  }

  public DeleteThreadDTO finalizarThread(final SessaoChatGPTDTO sessao) {
    return client.finalizarThread(sessao);
  }

  @SuppressWarnings({ "static-access" })
  private ArrayList<String> getText(final NextStepResponseDTO nextStepResponseDTO, final SessaoChatGPTDTO sessao) throws Exception {
    Thread.currentThread().sleep(AGUARDAR);
    final var respostas = new ArrayList<String>();
    MensagemPostedResponseDTO mensagemPostedResponseDTO;
    for (final NextStepDataResponseDTO item : nextStepResponseDTO.getData()) {
      mensagemPostedResponseDTO = client.lerMensagem(item.getStepDetails().getMessageCreation().getMessageId(), sessao);
      mensagemPostedResponseDTO.getContent().forEach(n -> respostas.add(n.getText().getValue()));
    }
    return respostas;

  }

  private void gravarLog(final RepostaResponseDTO itensDaMateriaRetornoGPT, final String uuid, final LocalDateTime inicio, final String pergunta) {
    try { // Grava o log da consulta.
      final var logs = itensDaMateriaRetornoGPT.getChoices().stream().map(choice -> convert.convert(itensDaMateriaRetornoGPT, choice, inicio, pergunta, uuid))
          .toList();
      logs.forEach(logDialogoChatGPTRepository::save);
    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Falha ao logar mensagens do ChatGPT no banco, mensagem:".concat(pergunta).concat(". Erro: ").concat(ex.getMessage()), ex);
    }

  }

  public String inciarConversa(final ConfiguracoesEntity configuracao) {
    final var id = client.criarThrend(configuracao).getId();
    properties.getChatgpt().setThread(id);
    return id;
  }

  public RepostaResponseDTO pergunta(final String pergunta, final String uuid, final LocalDateTime inicio, final SessaoChatGPTDTO sessao) {
    log.info("Pergunta feita ao chatGPT: ".concat(pergunta));
    final var resposta = client.conversar(new PromptRequestDTO(properties.getChatgpt().getModel(),
        Arrays.asList(new MessageChatGPTDTO(properties.getChatgpt().getRoleUser(), pergunta)), properties.getChatgpt().getTemperature()), sessao);

    log.info("Tokens usados ".concat(" tokens usados ").concat(resposta.getUsage().getTotalTokens().toString()));

    gravarLog(resposta, uuid, inicio, pergunta);

    return resposta;

  }

  @SuppressWarnings("static-access")
  public List<String> perguntarAssistente(final String pergunta, final String uuid, final LocalDateTime inicio, final SessaoChatGPTDTO sessao)
      throws Exception {
    final var inicioProcesso = Instant.now();
    final var respostas = new ArrayList<String>();
    client.conversar(pergunta, sessao);

    log.info("Pergunta feita ao chatGPT: ".concat(pergunta));

    final var runnerResponseDTO = client.iniciarChat(sessao);
    final var chatgptRunnerId = runnerResponseDTO.getId();

    var nextStepResponseDTO = existeResposta(runnerResponseDTO, chatgptRunnerId, sessao);
    if (isNull(nextStepResponseDTO)) {
      log.info("Voltou vazio");
      return null;
    }

    while (!COMPLETED.equals(nextStepResponseDTO.getData().get(0).getStatus())) {
      Thread.currentThread().sleep(AGUARDAR);
      if (INCOMPLETO.equals(nextStepResponseDTO.getStatus())) {
        respostas.addAll(getText(nextStepResponseDTO, sessao));
        client.conversar("continue", sessao);
        nextStepResponseDTO = existeResposta(runnerResponseDTO, chatgptRunnerId, sessao);
        if (isNull(nextStepResponseDTO)) {
          log.info("Voltou vazio 2");
          return null;
        }
      }
      nextStepResponseDTO = client.proximoPasso(chatgptRunnerId, nextStepResponseDTO.getStatus(), sessao);
      if (isNull(nextStepResponseDTO)) {
        log.info("Voltou vazio 3");
        return null;

      }
    }

    log.info("Demora para responder: " + Duration.between(inicioProcesso, Instant.now()).toSeconds());
    respostas.addAll(getText(nextStepResponseDTO, sessao));
    return respostas;
  }

}
