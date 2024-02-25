/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.FeignJoomlaConfig;
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

  @PostMapping("/content/articles")
  String gravar(@RequestBody AtributosArtigoJoomlaDTO artigo);

}