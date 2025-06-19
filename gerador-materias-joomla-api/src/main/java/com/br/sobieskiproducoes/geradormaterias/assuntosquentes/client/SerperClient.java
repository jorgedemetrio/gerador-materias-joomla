/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.assuntosquentes.client;

import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.br.sobieskiproducoes.geradormaterias.assuntosquentes.client.request.SerperRequestDTO;
import com.br.sobieskiproducoes.geradormaterias.assuntosquentes.client.response.SerperSearchResultDTO;
import com.br.sobieskiproducoes.geradormaterias.config.properties.ConfiguracoesProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 13 de jun. de 2025 20:24:50
 */
@Log
@RequiredArgsConstructor
@Component
public class SerperClient {

  public static final String X_API_KEY = "X-API-KEY";

  private final ConfiguracoesProperties properties;

  private final RestTemplate restTemplate;

  public SerperSearchResultDTO maisFalados(final String termo) {

    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add(X_API_KEY, properties.getSerper().getBearer());

    final SerperRequestDTO request = new SerperRequestDTO();

    request.setQ(termo);

    final HttpEntity<SerperRequestDTO> httpEntity = new HttpEntity<>(request, headers);

    ResponseEntity<SerperSearchResultDTO> resposta = null;
    try {
      resposta = restTemplate.exchange(properties.getSerper().getSearch(), HttpMethod.POST, httpEntity, SerperSearchResultDTO.class);
    } catch (final RestClientResponseException ex) {
      log.log(Level.SEVERE, String.format("[ERROR] SerperClient.maisFalados {messagem: %s, bosy: %s}", ex.getLocalizedMessage(), ex.getResponseBodyAsString()),
          ex.getCause());
    }

    return Objects.nonNull(resposta) ? resposta.getBody() : null;

  }

}
