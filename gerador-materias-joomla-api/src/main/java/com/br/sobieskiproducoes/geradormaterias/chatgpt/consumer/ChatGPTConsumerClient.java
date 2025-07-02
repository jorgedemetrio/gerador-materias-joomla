/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.request.MessageChatGPTDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.request.PromptRequestDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.request.RunnerRequestDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.DeleteThreadDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.MensagemPostedResponseDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.NextStepResponseDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.RepostaThrendsRunnerDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.RunnerResponseDTO;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.consumer.response.RunnerStatuEnum;
import com.br.sobieskiproducoes.geradormaterias.chatgpt.dto.SessaoChatGPTDTO;
import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormaterias.empresa.domain.ConfiguracoesEntity;

import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 13:19:37
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Component
public class ChatGPTConsumerClient {

  private static final String ORGANIZATION = "OpenAI-Organization";
  private static final String ASSISTENT = "OpenAI-Beta";
  private static final String ASSISTENT_VALOR = "assistants=v2";
  private final RestTemplate restTemplate;
  private final ConfiguracoesProperties properties;

  public RepostaResponseDTO conversar(final PromptRequestDTO dto, final SessaoChatGPTDTO sessao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(sessao.getConfiguracao().getChatgpt().getBearer());
    headers.add(ORGANIZATION, sessao.getConfiguracao().getChatgpt().getOrganization());
    headers.add(ASSISTENT, ASSISTENT_VALOR);
    // dto.setAssistantId(properties.getChatgpt().getAssistente());
    final HttpEntity<PromptRequestDTO> httpEntity = new HttpEntity<>(dto, headers);

    final ResponseEntity<RepostaResponseDTO> resposta = restTemplate.exchange(properties.getChatgpt().getUrl(), HttpMethod.POST, httpEntity,
        RepostaResponseDTO.class);

    return resposta.getBody();
  }

  public MensagemPostedResponseDTO conversar(final String pergunta, final SessaoChatGPTDTO sessao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(sessao.getConfiguracao().getChatgpt().getBearer());
    headers.add(ORGANIZATION, sessao.getConfiguracao().getChatgpt().getOrganization());
    headers.add(ASSISTENT, ASSISTENT_VALOR);

    final HttpEntity<MessageChatGPTDTO> httpEntity = new HttpEntity<>(new MessageChatGPTDTO(properties.getChatgpt().getRoleUser(), pergunta), headers);

    final ResponseEntity<MensagemPostedResponseDTO> resposta = restTemplate
        .exchange(properties.getChatgpt().getUrlPostarMensagem().formatted(sessao.getThreadId()), HttpMethod.POST, httpEntity, MensagemPostedResponseDTO.class);

    return resposta.getBody();
  }

  /**
   * Cria uma Thread
   *
   * @return
   */
  public RepostaThrendsRunnerDTO criarThrend(final ConfiguracoesEntity configuracao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(configuracao.getChatgpt().getBearer());
    headers.add(ASSISTENT, ASSISTENT_VALOR);

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<RepostaThrendsRunnerDTO> resposta = restTemplate.exchange(properties.getChatgpt().getUrlCriarThrend(), HttpMethod.POST, httpEntity,
        RepostaThrendsRunnerDTO.class);

    return resposta.getBody();
  }

  public DeleteThreadDTO finalizarThread(final SessaoChatGPTDTO sessao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(sessao.getConfiguracao().getChatgpt().getBearer());
    headers.add(ORGANIZATION, sessao.getConfiguracao().getChatgpt().getOrganization());
    headers.add(ASSISTENT, ASSISTENT_VALOR);

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<DeleteThreadDTO> resposta = restTemplate.exchange(properties.getChatgpt().getUrlApagarThrend().formatted(sessao.getThreadId()),
        HttpMethod.DELETE, httpEntity, DeleteThreadDTO.class);

    return resposta.getBody();
  }

  public RunnerResponseDTO iniciarChat(final SessaoChatGPTDTO sessao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(sessao.getConfiguracao().getChatgpt().getBearer());
    headers.add(ORGANIZATION, sessao.getConfiguracao().getChatgpt().getOrganization());
    headers.add(ASSISTENT, ASSISTENT_VALOR);

    final HttpEntity<RunnerRequestDTO> httpEntity = new HttpEntity<>(new RunnerRequestDTO(sessao.getConfiguracao().getChatgpt().getAssistente()), headers);

    final ResponseEntity<RunnerResponseDTO> resposta = restTemplate.exchange(properties.getChatgpt().getUrlIniciarRun().formatted(sessao.getThreadId()),
        HttpMethod.POST, httpEntity, RunnerResponseDTO.class);

    return resposta.getBody();
  }

  public MensagemPostedResponseDTO lerMensagem(final String mensagemId, final SessaoChatGPTDTO sessao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(sessao.getConfiguracao().getChatgpt().getBearer());
    headers.add(ORGANIZATION, sessao.getConfiguracao().getChatgpt().getOrganization());
    headers.add(ASSISTENT, ASSISTENT_VALOR);

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<MensagemPostedResponseDTO> resposta = restTemplate.exchange(
        properties.getChatgpt().getUrlLerMensagem().formatted(sessao.getThreadId(), mensagemId), HttpMethod.GET, httpEntity, MensagemPostedResponseDTO.class);

    return resposta.getBody();
  }

  public RunnerResponseDTO lerRunner(final String runnerId, final SessaoChatGPTDTO sessao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(sessao.getConfiguracao().getChatgpt().getBearer());
    headers.add(ORGANIZATION, sessao.getConfiguracao().getChatgpt().getOrganization());
    headers.add(ASSISTENT, ASSISTENT_VALOR);

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<RunnerResponseDTO> resposta = restTemplate
        .exchange(properties.getChatgpt().getUrlLerRunner().formatted(sessao.getThreadId(), runnerId), HttpMethod.GET, httpEntity, RunnerResponseDTO.class);

    return resposta.getBody();
  }

  public NextStepResponseDTO proximoPasso(final String runnerId, final RunnerStatuEnum status, final SessaoChatGPTDTO sessao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(sessao.getConfiguracao().getChatgpt().getBearer());
    headers.add(ORGANIZATION, sessao.getConfiguracao().getChatgpt().getOrganization());
    headers.add(ASSISTENT, ASSISTENT_VALOR);

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<NextStepResponseDTO> resposta = restTemplate.exchange(
        properties.getChatgpt().getUrlAvancarMessage().formatted(sessao.getThreadId(), runnerId), HttpMethod.GET, httpEntity, NextStepResponseDTO.class);

    final NextStepResponseDTO retorno = resposta.getBody();
    retorno.setStatus(status);
    return retorno;
  }

}
