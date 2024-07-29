/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.service;

import static com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RunnerStatuEnum.COMPLETED;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.ChatGPTConsumerClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.MessageChatGPTDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.PromptRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.DeleteThreadDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.MensagemPostedResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.NextStepDataResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.NextStepResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RunnerResponseDTO;
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

  private static final int AGUARDAR = 2000;


  private void gravarLog(final RepostaResponseDTO itensDaMateriaRetornoGPT, final String uuid, final LocalDateTime inicio, final String pergunta) {
    try { // Grava o log da consulta.
      final List<LogDialogoChatGPTEntity> logs = itensDaMateriaRetornoGPT.getChoices().stream()
          .map(choice -> convert.convert(itensDaMateriaRetornoGPT, choice, inicio, pergunta, uuid)).toList();
      logs.forEach(logDialogoChatGPTRepository::save);
    } catch (final Exception ex) {
      log.log(Level.SEVERE, "Falha ao logar mensagens do ChatGPT no banco, mensagem:".concat(pergunta).concat(". Erro: ").concat(ex.getMessage()), ex);
    }

  }

  public RepostaResponseDTO pergunta(final String pergunta, final String uuid, final LocalDateTime inicio) {
    log.info("Pergunta feita ao chatGPT: ".concat(pergunta));
    final RepostaResponseDTO resposta = client.conversar(new PromptRequestDTO(properties.getChatgpt().getModel(),
        Arrays.asList(new MessageChatGPTDTO(properties.getChatgpt().getRoleUser(), pergunta)), properties.getChatgpt().getTemperature()));

    log.info("Tokens usados ".concat(" tokens usados ").concat(resposta.getUsage().getTotalTokens().toString()));

    gravarLog(resposta, uuid, inicio, pergunta);

    return resposta;

  }

  public String inciarConversa() {
    String id = client.criarThrend().getId();
    properties.getChatgpt().setThread(id);
    return id;
  }

  public DeleteThreadDTO finalizarThread() {
    return client.finalizarThread(properties.getChatgpt().getThread());
  }

  @SuppressWarnings("static-access")
  public List<String> perguntarAssistente(final String pergunta, final String uuid, final LocalDateTime inicio) throws Exception {
    Instant inicioProcesso = Instant.now();
    ArrayList<String> respostas = new ArrayList<>();
    MensagemPostedResponseDTO mensagemPostedResponseDTO = client.conversar(pergunta, properties.getChatgpt().getThread());

    
    log.info("Pergunta feita ao chatGPT: ".concat(pergunta));

    RunnerResponseDTO runnerResponseDTO = client.iniciarChat(properties.getChatgpt().getThread());
    String chatgptRunnerId = runnerResponseDTO.getId();

    while (!COMPLETED.equals(runnerResponseDTO.getStatus())) {
      Thread.currentThread().sleep(AGUARDAR);
      runnerResponseDTO = client.lerRunner(properties.getChatgpt().getThread(), chatgptRunnerId);
    }

    NextStepResponseDTO nextStepResponseDTO = client.proximoPasso(properties.getChatgpt().getThread(), chatgptRunnerId);

    while (!COMPLETED.equals(nextStepResponseDTO.getData().get(0).getStatus())) {
      Thread.currentThread().sleep(AGUARDAR);
      nextStepResponseDTO = client.proximoPasso(properties.getChatgpt().getThread(), chatgptRunnerId);
    }
    Thread.currentThread().sleep(AGUARDAR);
    for (NextStepDataResponseDTO item : nextStepResponseDTO.getData()) {
      mensagemPostedResponseDTO = client.lerMensagem(properties.getChatgpt().getThread(), item.getStepDetails().getMessageCreation().getMessageId());
      mensagemPostedResponseDTO.getContent().forEach(n -> respostas.add(n.getText().getValue()));
    }
  
    log.info("Demora para responder: " + Duration.between(inicioProcesso, Instant.now()).toSeconds());

    return respostas;
  }

}
