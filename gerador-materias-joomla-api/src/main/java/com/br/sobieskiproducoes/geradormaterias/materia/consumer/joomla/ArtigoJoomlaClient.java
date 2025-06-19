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
import org.springframework.web.client.RestTemplate;

import com.br.sobieskiproducoes.geradormaterias.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormaterias.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormaterias.empresa.model.JoomlaConfigurationEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosArtigoJoomlaDTO;

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
public class ArtigoJoomlaClient {

  private static final String URL_BASE = "/content/articles";

  private static final String URL_BASE_CONCAT_URL = "/content/articles?";
  private static final String URL_BASE_CONCAT_PARAM = "/content/articles?page[limit]=";

  private static final String PARAM_PAGEE = "&page[offset]";

  private final RestTemplate restTemplate;

  public GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>> get(final JoomlaConfigurationEntity configuracao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(configuracao.getBearer());
    headers.setAccept(Collections.singletonList(MediaType.ALL));
    headers.setCacheControl(CacheControl.noCache());

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>>> resposta = restTemplate.exchange(
        configuracao.getUrl() + URL_BASE, HttpMethod.GET, httpEntity,
        new ParameterizedTypeReference<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>>>() {
        });

    return resposta.getBody();
  }

  public GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>> get(final String url, final JoomlaConfigurationEntity configuracao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(configuracao.getBearer());
    headers.setAccept(Collections.singletonList(MediaType.ALL));
    headers.setCacheControl(CacheControl.noCache());

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>>> resposta = restTemplate.exchange(
        configuracao.getUrl() + URL_BASE_CONCAT_URL + url, HttpMethod.GET, httpEntity,
        new ParameterizedTypeReference<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>>>() {
        });

    return resposta.getBody();
  }

  public GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>> get(final String offset, final String limit,
      final JoomlaConfigurationEntity configuracao) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setBearerAuth(configuracao.getBearer());
    headers.setAccept(Collections.singletonList(MediaType.ALL));
    headers.setCacheControl(CacheControl.noCache());

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

    final ResponseEntity<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>>> resposta = restTemplate.exchange(
        configuracao.getUrl() + URL_BASE_CONCAT_PARAM + limit + PARAM_PAGEE + offset, HttpMethod.GET, httpEntity,
        new ParameterizedTypeReference<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>>>>() {
        });

    return resposta.getBody();
  }

}
