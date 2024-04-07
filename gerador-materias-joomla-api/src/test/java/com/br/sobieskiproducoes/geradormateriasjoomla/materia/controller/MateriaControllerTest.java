/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoItemJoomlaResponse;
import com.br.sobieskiproducoes.geradormateriasjoomla.consumer.response.GenericoJoomlaDataDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.dto.AtributosArtigoSalvoJoomlaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PublicarDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.exception.ObjectoJaExiteNoBancoBusinessException;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.GerarMateriaService;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.MateriaJoomlaService;

/**
 * @author Ane Batista
 * @since 23 de mar. de 2024 16:57:35
 * @version 1.0.0  *   
 */

@ExtendWith(MockitoExtension.class)
public class MateriaControllerTest {
	@InjectMocks
	private MateriaController controller;

	@Mock
	private MateriaJoomlaService service;

	@Mock
	private GerarMateriaService gerarMateriaService;

	@Test
	public void publicarSuccessoTest() throws Exception {
		Long id = 1L;
		GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> mockResponse = new GenericoItemJoomlaResponse<>();
		when(service.publicarMateriaJoomla(id)).thenReturn(mockResponse);

		ResponseEntity<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>>> response = controller
				.publicar(id, new PublicarDTO());

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		verify(service).publicarMateriaJoomla(id);
		verifyNoInteractions(gerarMateriaService);
	}

	@Test
	public void publicarExistindoObjectExceptionTest() throws Exception {
		Long id = 1L;
		when(service.publicarMateriaJoomla(id))
				.thenThrow(new ObjectoJaExiteNoBancoBusinessException("Object already exists"));

		ResponseEntity<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>>> response = controller
				.publicar(id, new PublicarDTO());

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		verify(service).publicarMateriaJoomla(id);
		verifyNoInteractions(gerarMateriaService);
	}

	@Test
	public void publicarInternalServerErrorTest() throws Exception {
		Long id = 1L;
		when(service.publicarMateriaJoomla(id)).thenThrow(new RuntimeException("Internal server error"));

		ResponseEntity<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>>> response = controller
				.publicar(id, new PublicarDTO());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		verify(service).publicarMateriaJoomla(id);
		verifyNoInteractions(gerarMateriaService);
	}

	@Test
	public void sugerirMateriaSuccessoTest() throws Exception {
	    List<String> termos = new ArrayList<>();
	    termos.add("materia");
	    List<String> audiencias = new ArrayList<>();
	    audiencias.add("leitores");
	    PropostaMateriaDTO propostaMateriaDTO = new PropostaMateriaDTO();
	    propostaMateriaDTO.setId(1L);
	    List<String> titulos = new ArrayList<>();
	    titulos.add("Título da matéria");
	    propostaMateriaDTO.setTitulos(titulos);

	    List<PropostaMateriaDTO> mockResponse = new ArrayList<>();
	    mockResponse.add(propostaMateriaDTO);

	    
	    SugerirMateriaDTO request = new SugerirMateriaDTO();
	    request.setTema("Teste");
	    request.setTermos(Arrays.asList("materia"));
	    request.setPublicar(LocalDateTime.now());
	    request.setCategoria(10L);
	    request.setAudiencias(Arrays.asList("leitores"));

	    when(gerarMateriaService.gerarSugestaoMateria(request)).thenReturn(mockResponse);

	    ResponseEntity<List<PropostaMateriaDTO>> response = controller.sugerirMateria(request);

	    assertEquals(HttpStatus.CREATED, response.getStatusCode());
	    verify(gerarMateriaService).gerarSugestaoMateria(request);

	    
	}
}
