/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.CargaDadosImagensProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ChatGPTConfigurationProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.ConfiguracoesProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.DadosImagensLogoProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.DadosImagensMateriasProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.JoomlaConfigurationProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.PosicaoEnum;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.CategoriaService;

/**
 * @author Jorge Demetrio
 * @since 21 de fev. de 2024 20:11:34
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {

  @InjectMocks
  CategoriaController categoriaController;

  @Mock
  CategoriaService service;

  @Spy
  ConfiguracoesProperties properties = new ConfiguracoesProperties(
      new CargaDadosImagensProperties(
          new DadosImagensMateriasProperties("pastaImagemMaterias", "autor", 1, 1, 2.4d, "constante", "url",
              Boolean.TRUE),
          new DadosImagensLogoProperties("path", PosicaoEnum.ALEATORIO, PosicaoEnum.ALEATORIO, 1, 1, 2.5d, 1, 2.4d)),
      new ChatGPTConfigurationProperties("url", "bearer", "assistente", "organization", "model", 2.3d, "roleUser",
          "roleSystem", "roleAssistant", "maxTokens"),
      new JoomlaConfigurationProperties("url", "bearer", "idioma"));

  @Test
  void testAtualizar() {
    final Map<String, Integer> retornoItem = new HashMap<>();
    retornoItem.put("total", 10);
    when(service.atualizarBancoCategoria()).thenReturn(retornoItem);

    final ResponseEntity<Map<String, Integer>> retorno = categoriaController.atualizar();

    verify(service, times(1)).atualizarBancoCategoria();

    assertEquals(HttpStatus.OK, retorno.getStatusCode());
    assertEquals(10, retorno.getBody().get("total"));

  }

}
