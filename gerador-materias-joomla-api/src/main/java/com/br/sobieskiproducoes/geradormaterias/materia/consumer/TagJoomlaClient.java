/**
 *
 */
package com.br.sobieskiproducoes.geradormaterias.materia.consumer;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.sobieskiproducoes.geradormaterias.config.FeignJoomlaConfig;
import com.br.sobieskiproducoes.geradormaterias.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormaterias.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormaterias.materia.consumer.dto.joomla.AtributosTagJoomlaDTO;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 18:25:40
 * @version 1.0.0
 */
@FeignClient(name = "tagJoomlaClient", url = "${configuracao.joomla.url}", configuration = FeignJoomlaConfig.class)
public interface TagJoomlaClient {

  @GetMapping(path = "/tags/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
  GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> getTagById(@PathVariable("id") String Long);

  @GetMapping(path = "/tags", consumes = { MediaType.APPLICATION_JSON_VALUE })
  GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> getTags();

  @GetMapping(path = "/tags?{url}", consumes = { MediaType.APPLICATION_JSON_VALUE })
  GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> getTags(
      @PathVariable("url") String url);

  @GetMapping(path = "/tags", consumes = { MediaType.APPLICATION_JSON_VALUE })
  GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> getTags(
      @RequestParam("page[offset]") String offset,
      @RequestParam("page[limit]") String limit);

}
