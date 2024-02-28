/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.PromptRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;

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
  private static final String ASSISTENT_VALOR = "assistants=v1";
  private final RestTemplate restTemplate;
  private final ConfiguracoesProperties properties;

  public RepostaResponseDTO conversar(final PromptRequestDTO dto) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(properties.getChatgpt().getBearer());
    headers.add(ORGANIZATION, properties.getChatgpt().getOrganization());
    headers.add(ASSISTENT, ASSISTENT_VALOR);
    // dto.setAssistantId(properties.getChatgpt().getAssistente());
    final HttpEntity<PromptRequestDTO> httpEntity = new HttpEntity<>(dto, headers);

    final ResponseEntity<RepostaResponseDTO> resposta = restTemplate.exchange(properties.getChatgpt().getUrl(),
        HttpMethod.POST,
        httpEntity, RepostaResponseDTO.class);

    return resposta.getBody();
  }

}
