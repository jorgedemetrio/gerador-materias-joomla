/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PropostaMateriaDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.MateriaEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.MateriaRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.MateriaConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.MateriaConvertImpl;

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
    MateriaConvert convert = new MateriaConvertImpl();

	@Test
	void  apagarMateriaTest() {
		final Long id = 10L;
		final Optional<MateriaEntity> materiaEntityOpt = repository.findById(id);
		when(repository.findById(id)).thenReturn(materiaEntityOpt);
		assertFalse(service.apagar(id));

	}

	@Test
	void buscarMateriasTest() {
			final Long id = 10L;
		 	final MateriaEntity materiaEntity = new MateriaEntity();
            materiaEntity.setId(id);
            when(repository.findById(anyLong())).thenReturn(Optional.of(materiaEntity));

		    final PropostaMateriaDTO propostaMateriaDTO = service.getBuscarMateria(id);

		    final ArgumentCaptor<Long> capIdLong = ArgumentCaptor.forClass(Long.class);
		    verify(repository, times(1)).findById(capIdLong.capture());
		    assertEquals(id, capIdLong.getValue());

		    assertNotNull(propostaMateriaDTO); // Verifica se propostaMateriaDTO não é null
		    assertEquals(id, propostaMateriaDTO.getId());
	}


}
