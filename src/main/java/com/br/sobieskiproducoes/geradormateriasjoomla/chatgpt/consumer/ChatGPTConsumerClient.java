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
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTConfigurationProperties;

import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 13:19:37
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Component
public class ChatGPTConsumerClient {

  private final RestTemplate restTemplate;
  private final ChatGPTConfigurationProperties properties;

  public RepostaResponseDTO conversar(final PromptRequestDTO dto) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(properties.getBearer());
    final HttpEntity<PromptRequestDTO> httpEntity = new HttpEntity<>(dto, headers);

    final ResponseEntity<RepostaResponseDTO> resposta = restTemplate.exchange(properties.getUrl(), HttpMethod.POST,
        httpEntity, RepostaResponseDTO.class);

    return resposta.getBody();
  }

}
