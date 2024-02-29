/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.FeignJoomlaConfig;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosCategoriaJoomlaDTO;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 18:25:40
 * @version 1.0.0
 */
@FeignClient(name = "categoriaJoomlaClient", url = "${configuracao.joomla.url}", dismiss404 = true, configuration = FeignJoomlaConfig.class)
public interface CategoriaJoomlaClient {

  @GetMapping(path = "/content/categories", consumes = { MediaType.APPLICATION_JSON_VALUE })
  GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>> getCategorias();

  @GetMapping(path = "/content/categories?{url}", consumes = { MediaType.APPLICATION_JSON_VALUE })
  GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>> getCategorias(
      @PathVariable("url") String url);

  @GetMapping(path = "/content/categories", consumes = { MediaType.APPLICATION_JSON_VALUE })
  GenericoItemJoomlaResponse<List<GenericoJoomlaDataDTO<AtributosCategoriaJoomlaDTO>>> getCategorias(
      @RequestParam("page[offset]") String offset,
      @RequestParam("page[limit]") String limit);


}
