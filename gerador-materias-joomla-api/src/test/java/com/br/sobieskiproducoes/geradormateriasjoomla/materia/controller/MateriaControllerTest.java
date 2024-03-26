/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

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
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PublicarDTO;
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
        GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>> mockResponse = 
                new GenericoItemJoomlaResponse<>();
        when(service.publicarMateriaJoomla(id)).thenReturn(mockResponse);

        ResponseEntity<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>>> response = controller.publicar(id, new PublicarDTO());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(service).publicarMateriaJoomla(id);
        verifyNoInteractions(gerarMateriaService);
    }

    @Test
    public void publicarExistindoObjectExceptionTest() throws Exception {
        Long id = 1L;
        when(service.publicarMateriaJoomla(id)).thenThrow(new ObjectoJaExiteNoBancoBusinessException("Object already exists"));

        ResponseEntity<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>>> response = controller.publicar(id, new PublicarDTO());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service).publicarMateriaJoomla(id);
        verifyNoInteractions(gerarMateriaService);
    }

    @Test
    public void publicarInternalServerErrorTest() throws Exception {
        Long id = 1L;
        when(service.publicarMateriaJoomla(id)).thenThrow(new RuntimeException("Internal server error"));

        
        ResponseEntity<GenericoItemJoomlaResponse<GenericoJoomlaDataDTO<AtributosArtigoSalvoJoomlaDTO>>> response = controller.publicar(id, new PublicarDTO());

        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(service).publicarMateriaJoomla(id);
        verifyNoInteractions(gerarMateriaService); 
    }

}
