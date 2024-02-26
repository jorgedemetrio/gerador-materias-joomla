/**
 *
 */
package com.br.sobieskiproducoes.geradormateriasjoomla.materia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.consumer.TagJoomlaClient;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.model.TagEntity;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.repository.TagRepository;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.TagConvert;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.service.convert.TagConvertImpl;

/**
 * @author Jorge Demetrio
 * @since 26 de fev. de 2024 01:05:36
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class TagServiceTest {

  @InjectMocks
  private TagService service;

  @Mock
  private TagRepository repository;

  @Spy
  private final TagConvert convert = new TagConvertImpl();

  @Mock
  private TagJoomlaClient client;

  @Test
  void apagarTest() {
    // Cenário
    final Long id = 10L;
    final String titulo = "Titulo";
    final TagEntity tag = new TagEntity();
    tag.setId(id);
    tag.setTitulo(titulo);
    final Optional<TagEntity> tagEntityOpt = Optional.of(tag);
    when(repository.findById(anyLong())).thenReturn(tagEntityOpt);

    // Chamada do teste, como ele retorna um objeto simples já estou validadndo se
    // está retorando True.
    // Se fosse um objeto complexo receberia em uma variavel e validaria cara
    // atributo.
    assertTrue(service.apagar(id));

    // Validação do resultado do teste e suas chamadas internas
    final ArgumentCaptor<TagEntity> capTagEntity = ArgumentCaptor.forClass(TagEntity.class);
    final ArgumentCaptor<Long> capIdLong = ArgumentCaptor.forClass(Long.class);

    verify(repository, times(1)).delete(capTagEntity.capture());
    verify(repository, times(1)).findById(capIdLong.capture());

    assertEquals(id, capIdLong.getValue());
    assertEquals(id, capTagEntity.getValue().getId());
    assertEquals(titulo, capTagEntity.getValue().getTitulo());
  }

  @Test
  void apagarTest_TagEntityNotFound() {

    final Long id = 10L;
    final Optional<TagEntity> tagEntityOpt = Optional.empty();
    when(repository.findById(id)).thenReturn(tagEntityOpt);
    assertFalse(service.apagar(id));
    // assertThrows(BusinessException.class, () -> service.apagar(1L));
  }


}
