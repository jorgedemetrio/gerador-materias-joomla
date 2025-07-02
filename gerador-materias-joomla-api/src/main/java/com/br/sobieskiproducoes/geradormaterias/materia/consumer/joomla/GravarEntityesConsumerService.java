/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.consumer.joomla;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.logging.Level;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.br.sobieskiproducoes.geradormaterias.empresa.domain.JoomlaConfigurationEntity;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosArtigoJoomlaSalvarJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosArtigoSalvoJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosTagJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.ErrosJoomlaDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.ItemErroJoomlaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Jorge Demetrio
 * @since 28 de fev. de 2024 20:08:57
 * @version 1.0.0
 */
@Log
@RequiredArgsConstructor
@Component
public class GravarEntityesConsumerService {
    private static final String ERRO_SAME_ALIAS = "Save failed with the following error: Another Article in this category has the same alias.";
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> gravarArtigo(final AtributosArtigoJoomlaSalvarJoomlaDTO dto,
            final JoomlaConfigurationEntity configuracao) {
        log.info("Gravando aritgo: ".concat(dto.getTitle()));
        GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> retorno = null;
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(configuracao.getBearer());

        try {
            final String bodyEnvio = objectMapper.writeValueAsString(dto);
            headers.setContentLength(bodyEnvio.getBytes(StandardCharsets.UTF_8).length);
            final HttpEntity<String> httpEntity = new HttpEntity<>(bodyEnvio, headers);

            final ResponseEntity<String> resposta = restTemplate.exchange(configuracao.getUrl().concat("/content/articles"), HttpMethod.POST, httpEntity,
                    String.class);

            if (HttpStatus.OK.equals(resposta.getStatusCode())) {
                retorno = objectMapper.readValue(resposta.getBody(),
                        new TypeReference<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>>>() {
                        });
            }
        } catch (final HttpClientErrorException e) {

            try {
                final ErrosJoomlaDTO erros = objectMapper.readValue(e.getResponseBodyAsString(), ErrosJoomlaDTO.class);
                for (final ItemErroJoomlaDTO itemErro : erros.getErrors()) {
                    if (ERRO_SAME_ALIAS.equalsIgnoreCase(itemErro.getTitle().trim())) {
                        dto.setAlias(dto.getAlias() + "-" + String.format("%x", (int) (Math.random() * 255)).trim().toLowerCase());
                        return gravarArtigo(dto, configuracao);
                    }
                }
            } catch (final Exception ex) {
                log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
            log.info("Erro:\n".concat(e.getResponseBodyAsString()));
            log.log(Level.SEVERE, e.getLocalizedMessage() + " " + e.getStatusText(), e.getMostSpecificCause());
        } catch (final RestClientException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e.getMostSpecificCause());

        } catch (final JsonProcessingException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return retorno;
    }

    public GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> gravarTag(final AtributosTagJoomlaDTO dto,
            final JoomlaConfigurationEntity configuracao) {

        log.info("Gravando tag: ".concat(dto.getTitle()));
        GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> retorno = null;
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.setCacheControl(CacheControl.noCache());

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(configuracao.getBearer());
        try {
            final String bodyEnvio = objectMapper.writeValueAsString(dto);
            headers.setContentLength(bodyEnvio.getBytes(StandardCharsets.UTF_8).length);
            final HttpEntity<String> httpEntity = new HttpEntity<>(bodyEnvio, headers);

            final ResponseEntity<String> resposta = restTemplate.exchange(configuracao.getUrl().concat("/tags"), HttpMethod.POST, httpEntity, String.class);

            if (HttpStatus.OK.equals(resposta.getStatusCode())) {
                retorno = objectMapper.readValue(resposta.getBody(),
                        new TypeReference<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>>() {
                        });
            }
        } catch (final HttpClientErrorException e) {
            log.info("Erro:\n".concat(e.getResponseBodyAsString()));
            log.log(Level.SEVERE, e.getLocalizedMessage() + " " + e.getStatusText(), e.getMostSpecificCause());
        } catch (final RestClientException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e.getMostSpecificCause());

        } catch (final JsonProcessingException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return retorno;
    }

}
