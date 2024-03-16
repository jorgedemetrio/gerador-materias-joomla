/**
 * 
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.TagConvert;

/**
 * @author Ane Batista
 * @since 15 de mar. de 2024 15:45:00
 * @version 1.0.0
 * 
 */

@ExtendWith(MockitoExtension.class)
public class MateriaServiceTest {
	
	@InjectMocks
	MateriaService service;
	
	@Mock
	MateriaRepository repository;
	
	@Spy
	TagConvert convert;
	
	@Test
	void  apagarMateriaTest() {
		final Long id = 10L;
		final Optional<MateriaEntity> materiaEntityOpt = repository.findById(id);
		when(repository.findById(id)).thenReturn(materiaEntityOpt);
		assertFalse(service.apagar(id));
		
	}
}
