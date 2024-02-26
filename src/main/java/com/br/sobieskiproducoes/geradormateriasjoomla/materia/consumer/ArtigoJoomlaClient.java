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
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoJoomlaDTO;
import feign.Headers;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 18:25:40
 * @version 1.0.0
 */
@FeignClient(name = "materiaJoomlaClient", url = "${configuracao.joomla.url}", dismiss404 = true, configuration = FeignJoomlaConfig.class)
@Headers({ "Authorization: Bearer ${configuracao.joomla.bearer}" })
public interface ArtigoJoomlaClient {

  @GetMapping(path = "/content/articles")
  GenericoItemJoomlaResponse<List<AtributosArtigoJoomlaDTO>> get();

  @GetMapping("/content/articles?{url}")
  GenericoItemJoomlaResponse<List<AtributosArtigoJoomlaDTO>> get(@PathVariable("url") String url);

  @GetMapping("/content/articles")
  GenericoItemJoomlaResponse<List<AtributosArtigoJoomlaDTO>> get(@RequestParam("page[offset]") String offset,
      @RequestParam("page[limit]") String limit);

  @PostMapping("/content/articles")
  GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoJoomlaDTO>> gravar(
      @RequestBody AtributosArtigoJoomlaDTO artigo);

}
