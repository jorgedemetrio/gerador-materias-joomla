/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.consumer.joomla;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.JoomlaConfigurationEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosTagJoomlaDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 18:25:40
 * @version 1.0.0
 */

@Component
@Log
@RequiredArgsConstructor
public class TagJoomlaClient {

  private static final String URL_BASE = "/tags";

  private static final String URL_BASE_ID = "/tags/";

  private static final String URL_BASE_CONCAT_URL = "/tags?";
  private static final String URL_BASE_CONCAT_PARAM = "/tags?page[limit]=";

  private static final String PARAM_PAGEE = "&page[offset]";

  private final RestTemplate restTemplate;

  @GetMapping(path = "/tags/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
  public GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> getTagById(final String id, final JoomlaConfigurationEntity configuracao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(configuracao.getBearer());
    headers.setAccept(Collections.singletonList(MediaType.ALL));
    headers.setCacheControl(CacheControl.noCache());

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> resposta = restTemplate.exchange(
        configuracao.getUrl() + URL_BASE_ID + id, HttpMethod.GET, httpEntity,
        new ParameterizedTypeReference<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>>() {
        });

    return resposta.getBody();
  }

  public GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> getTags(final JoomlaConfigurationEntity configuracao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(configuracao.getBearer());
    headers.setAccept(Collections.singletonList(MediaType.ALL));
    headers.setCacheControl(CacheControl.noCache());

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>>> resposta = restTemplate.exchange(
        configuracao.getUrl() + URL_BASE, HttpMethod.GET, httpEntity,
        new ParameterizedTypeReference<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>>>() {
        });

    return resposta.getBody();
  }

  public GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> getTags(final String url,
      final JoomlaConfigurationEntity configuracao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(configuracao.getBearer());
    headers.setAccept(Collections.singletonList(MediaType.ALL));
    headers.setCacheControl(CacheControl.noCache());

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>>> resposta = restTemplate.exchange(
        configuracao.getUrl() + URL_BASE_CONCAT_URL + url, HttpMethod.GET, httpEntity,
        new ParameterizedTypeReference<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>>>() {
        });

    return resposta.getBody();
  }

  public GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> getTags(final String offset, final String limit,
      final JoomlaConfigurationEntity configuracao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(configuracao.getBearer());
    headers.setAccept(Collections.singletonList(MediaType.ALL));
    headers.setCacheControl(CacheControl.noCache());

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>>> resposta = restTemplate.exchange(
        configuracao.getUrl() + URL_BASE_CONCAT_PARAM + limit + PARAM_PAGEE + offset, HttpMethod.GET, httpEntity,
        new ParameterizedTypeReference<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>>>() {
        });

    return resposta.getBody();
  }
}
