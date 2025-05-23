/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer;

import java.util.List;
import java.util.logging.Level;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosCategoriaJoomlaDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 *
 * @author Jorge Demetrio
 * @version 1.0
 * @since 15 de mai. de 2025 23:16:18
 */
@Log
@Component
@RequiredArgsConstructor
public class CategoriaJoomlaRestTemplateClient {

  private final RestTemplate restTemplate;
  private final ConfiguracoesProperties properties;

  public GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>> getCategorias() {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(properties.getJoomla().getBearer());
    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
    try {
      final ResponseEntity<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>>> resposta = restTemplate.exchange(
          properties.getJoomla().getUrl() + "/content/categories", HttpMethod.GET, httpEntity,
          new ParameterizedTypeReference<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>>>() {
          });

      return resposta.getBody();
    } catch (final Exception ex) {
      log.log(Level.SEVERE, ex.getMessage(), ex.getCause());
      throw ex;
    }
  }

  public GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>> getCategorias(final String url) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(properties.getJoomla().getBearer());
    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
    try {
      final ResponseEntity<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>>> resposta = restTemplate.exchange(
          properties.getJoomla().getUrl() + "/content/categories?" + url, HttpMethod.GET, httpEntity,
          new ParameterizedTypeReference<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>>>() {
          });

      return resposta.getBody();
    } catch (final Exception ex) {
      log.log(Level.SEVERE, ex.getMessage(), ex.getCause());
      throw ex;
    }
  }

  public GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>> getCategorias(final String offset, final String limit) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(properties.getJoomla().getBearer());
    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
    try {
      final ResponseEntity<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>>> resposta = restTemplate.exchange(
          properties.getJoomla().getUrl() + "/content/categories?page[offset]=" + offset + "page[limit]=" + limit, HttpMethod.GET, httpEntity,
          new ParameterizedTypeReference<GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>>>() {
          });

      return resposta.getBody();
    } catch (final Exception ex) {
      log.log(Level.SEVERE, ex.getMessage(), ex.getCause());
      throw ex;
    }
  }

}
