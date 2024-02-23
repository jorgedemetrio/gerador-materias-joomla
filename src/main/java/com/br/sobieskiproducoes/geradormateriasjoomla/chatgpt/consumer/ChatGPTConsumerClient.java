/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.request.PromptRequestDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.chatgpt.consumer.response.RepostaResponseDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.FeignChatGPT4Config;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * @author Jorge Demetrio
 * @since 22 de fev. de 2024 13:19:37
 * @version 1.0.0
 */
@FeignClient(name = "categoriaJoomlaClient", url = "${configuracao.chatgpt.url}", dismiss404 = true, configuration = FeignChatGPT4Config.class)
public interface ChatGPTConsumerClient {

  @PostMapping(path = "completions", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
      MediaType.APPLICATION_JSON_VALUE })
  RepostaResponseDTO conversar(@RequestBody PromptRequestDTO dto);

}
