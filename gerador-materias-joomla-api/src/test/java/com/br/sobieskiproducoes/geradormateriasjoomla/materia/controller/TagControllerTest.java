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
import com.br.sobieskiproducoes.geradormateriasjoomla.config.properties.YoutubeConfigurationProperties;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.TagDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.TagService;

/**
 * @author Ane Batista
 * @since 21 de mar. de 2024 02:04:00
 * @version 1.0.0
 * 
 */

@ExtendWith(MockitoExtension.class)
public class TagControllerTest {
	@InjectMocks
	TagController tagController;
	
	@Mock
	TagService service;
	
	@Spy
	ConfiguracoesProperties properties = new ConfiguracoesProperties(
	      new CargaDadosImagensProperties(
	          new DadosImagensMateriasProperties("pastaImagemMaterias", "autor", 1, 1, 2.4d, "constante", "url",
	              Boolean.TRUE),
	          new DadosImagensLogoProperties("path", PosicaoEnum.ALEATORIO, PosicaoEnum.ALEATORIO, 1, 1, 2.5d, 1, 2.4d)),
	      new ChatGPTConfigurationProperties("url", "bearer", "assistente", "organization", "model", 2.3d, "roleUser",
	          "roleSystem", "roleAssistant", "maxTokens"),
	      new JoomlaConfigurationProperties("url", "bearer", "idioma"), new YoutubeConfigurationProperties());
	  
	@Test
	void buscaListaDeTagPorTituloTest() {
		final Map<String, Integer> retornoTag = new HashMap<>();
		retornoTag.put("total", 10);
		when(service.atualizarBancoTag()).thenReturn(retornoTag);
		
		final ResponseEntity<Map<String, Integer>> retorno = tagController.atualizar();
		
		verify(service, times(1)).atualizarBancoTag();
		
	    assertEquals(HttpStatus.OK, retorno.getStatusCode());
	    assertEquals(10, retorno.getBody().get("total"));
	}
	
	 @Test
	    public void buscarItem_RetornaNotFoundQuandoNull() {
	        when(service.buscarPorId(1L)).thenReturn(null);

	        ResponseEntity<TagDTO> result = tagController.buscarItem(1L);

	        assertEquals(ResponseEntity.notFound().build(), result);
	    }

	    @Test
	    public void buscarItem_RetornaOkQuandoNaoNullTest() {
	        TagDTO tag = new TagDTO();
	        when(service.buscarPorId(1L)).thenReturn(tag);

	        ResponseEntity<TagDTO> result = tagController.buscarItem(1L);

	        assertEquals(ResponseEntity.ok(tag), result);
	    }
	    
	    @Test
	    public void salvar_RetornarOkQuandoTagNaoNull() {
	        TagDTO tag = new TagDTO();
	        tag.setTitulo("Teste");
	        tag.setTitulo("Descrição do teste");

	        TagDTO tagSalva = new TagDTO();
	        tagSalva.setId(10L);
	        tagSalva.setTitulo("Teste");
	        tagSalva.setApelido("Apelido do teste");

	        when(service.gravar(tag)).thenReturn(tagSalva);

	        ResponseEntity<TagDTO> result = tagController.salvar(tag);


	        assertEquals(ResponseEntity.ok(tagSalva), result);
	    }
	 
}
