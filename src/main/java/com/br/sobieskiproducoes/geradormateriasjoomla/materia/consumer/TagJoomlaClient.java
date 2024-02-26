/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.FeignJoomlaConfig;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosTagJoomlaDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 18:25:40
 * @version 1.0.0
 */
@FeignClient(name = "tagJoomlaClient", url = "${configuracao.joomla.url}", dismiss404 = true, configuration = FeignJoomlaConfig.class)
public interface TagJoomlaClient {

  @GetMapping("/tags/{id}")
  GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> getTagById(@PathVariable("id") String Long);

  @GetMapping(path = "/tags")
  GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> getTags();

  @GetMapping("/tags?{url}")
  GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>>> getTags(
      @PathVariable("url") String url);

  @GetMapping("/tags")
  GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> getTags(
      @RequestParam("page[offset]") String offset,
      @RequestParam("page[limit]") String limit);

  @PostMapping("/tags")
  GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosTagJoomlaDTO>> gravarTag(
      @RequestBody AtributosTagJoomlaDTO artigo);
}
