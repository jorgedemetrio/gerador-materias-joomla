/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.FeignConfig;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.ItemResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.CategoriaJoomlaDTO;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 18:25:40
 * @version 1.0.0
 */
@FeignClient(name = "categoriaJoomlaClient", url = "${configuracao.joomla.url}", dismiss404 = true, configuration = FeignConfig.class)
public interface CategoriaJoomlaClient {

  @GetMapping("/content/categories")
  ItemResponse<CategoriaJoomlaDTO> getCategorias();

  @GetMapping("/content/categories?{url}")
  ItemResponse<CategoriaJoomlaDTO> getCategorias(@PathVariable("url") String url);

  @GetMapping("/content/categories")
  ItemResponse<CategoriaJoomlaDTO> getCategorias(@RequestParam("page[offset]") String offset,
      @RequestParam("page[limit]") String limit);
}
